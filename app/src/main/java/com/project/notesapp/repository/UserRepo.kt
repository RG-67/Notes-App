package com.project.notesapp.repository

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.notesapp.api.UserApi
import com.project.notesapp.dao.UserDao
import com.project.notesapp.model.AuthModel
import com.project.notesapp.model.userRequestModel.UserRequest
import com.project.notesapp.model.userResponseModel.UserResponse
import com.project.notesapp.ui.authentication.AuthViewModel
import com.project.notesapp.utils.NetworkResult
import com.project.notesapp.utils.PreferenceHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.invoke
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepo @Inject constructor(
    private val preferenceHelper: PreferenceHelper,
    private val userDao: UserDao,
    private val userApi: UserApi
) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLiveData: LiveData<NetworkResult<UserResponse>> get() = _userResponseLiveData

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

    fun clearPreference() {
        preferenceHelper.clearSharedPreference()
    }

    /*set api*/
    suspend fun createUser(userRequest: UserRequest) {
        try {
            _userResponseLiveData.postValue(NetworkResult.Loading())
            val response = userApi.createUser(userRequest)
            Log.d("UserBodySuccess ==>", response.toString())
            handleResponse(response)
        } catch (e: Exception) {
            Log.d("CreateUserException ==>", "${e.message}")
        }
    }

    private fun handleResponse(userResponse: Response<UserResponse>) {
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