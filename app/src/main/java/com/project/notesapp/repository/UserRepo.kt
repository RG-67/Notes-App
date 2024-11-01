package com.project.notesapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.notesapp.api.UserApi
import com.project.notesapp.dao.UserDao
import com.project.notesapp.model.AuthModel
import com.project.notesapp.model.userRequestModel.UserLoginRequest
import com.project.notesapp.model.userRequestModel.UserRegisterRequest
import com.project.notesapp.model.userResponseModel.UserLoginResponse
import com.project.notesapp.model.userResponseModel.UserRegisterResponse
import com.project.notesapp.utils.NetworkResult
import com.project.notesapp.utils.PreferenceHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepo @Inject constructor(
    private val preferenceHelper: PreferenceHelper,
    private val userDao: UserDao,
    private val userApi: UserApi
) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserRegisterResponse>>()
    private val _userLoginResponseData = MutableLiveData<NetworkResult<UserLoginResponse>>()
    val userResponseLiveData: LiveData<NetworkResult<UserRegisterResponse>> get() = _userResponseLiveData
    val userLoginResponseData: LiveData<NetworkResult<UserLoginResponse>> get() = _userLoginResponseData

    suspend fun isExists(userName: String): Int = userDao.checkIsExists(userName)

    suspend fun insertUser(authModel: AuthModel) = withContext(Dispatchers.IO) {
        userDao.insertUser(authModel)
    }

    val getUser: Flow<List<AuthModel>> = userDao.getUser()

    suspend fun getUserLogin(userName: String, password: String): List<AuthModel> =
        withContext(Dispatchers.IO) {
            userDao.getUserLogin(userName, password)
        }

    fun getUserId(): String? {
        return preferenceHelper.getUserId()
    }

    fun setUserId(userId: String) {
        preferenceHelper.setUserId(userId)
    }

    fun getUserName(): String? {
        return preferenceHelper.getUserName()
    }

    fun setUserName(userName: String) {
        preferenceHelper.setUserName(userName)
    }

    fun getUserEmail(): String? {
        return preferenceHelper.getUserEmail()
    }

    fun setUserEmail(userEmail: String) {
        preferenceHelper.setUserEmail(userEmail)
    }

    fun getLoginEmail(): String? {
        return preferenceHelper.getLoginEmail()
    }

    fun setLoginEmail(loginEmail: String) {
        preferenceHelper.setLoginEmail(loginEmail)
    }

    fun getUserPhone(): String? {
        return preferenceHelper.getUserPhone()
    }

    fun setUserPhone(userPhone: String) {
        preferenceHelper.setUserPhone(userPhone)
    }

    fun getDBGenerateId(): String? {
        return preferenceHelper.getDBGenerateId()
    }

    fun setDBGenerateId(dbGenerateId: String) {
        preferenceHelper.setDBGenerateId(dbGenerateId)
    }

    fun clearPreference() {
        preferenceHelper.clearSharedPreference()
    }

    /*set api*/
    suspend fun createUser(userRequest: UserRegisterRequest) {
        try {
            _userResponseLiveData.postValue(NetworkResult.Loading())
            val response = userApi.createUser(userRequest)
            Log.d("UserBodySuccess ==>", response.toString())
            handleResponse(response)
        } catch (e: Exception) {
            Log.d("CreateUserException ==>", "${e.message}")
        }
    }

    suspend fun loginUser(userLoginRequest: UserLoginRequest) {
        try {
            _userLoginResponseData.postValue(NetworkResult.Loading())
            val response = userApi.loginUser(userLoginRequest)
            Log.d("UserLoginBodySuccess ==>", response.toString())
            handleLoginResponse(response)
        } catch (e: Exception) {
            Log.d("UserLoginBodyError ==>", "${e.message}")
            e.printStackTrace()
        }
    }

    private fun handleLoginResponse(response: Response<UserLoginResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userLoginResponseData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userLoginResponseData.postValue(NetworkResult.Error(errorObj.getString("msg")))
        } else {
            _userLoginResponseData.postValue(NetworkResult.Error("msg"))
        }
    }

    private fun handleResponse(userResponse: Response<UserRegisterResponse>) {
        if (userResponse.isSuccessful && userResponse.body() != null) {
            Log.d("UserBodyHandleSuccess ==>", userResponse.body().toString())
            _userResponseLiveData.postValue(NetworkResult.Success(userResponse.body()!!))
        } else if (userResponse.errorBody() != null) {
            Log.d("UserBodyError ==>", userResponse.errorBody().toString())
            val errorObj = JSONObject(userResponse.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("msg")))
        } else {
            Log.d("UserBodyErrorMsg ==>", "ABCDE..")
            _userResponseLiveData.postValue(NetworkResult.Error("msg"))
        }
    }

}