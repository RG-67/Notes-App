package com.project.notesapp.ui.authentication

import android.app.Activity
import android.app.AlertDialog
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.messaging.FirebaseMessaging
import com.project.notesapp.R
import com.project.notesapp.model.AuthModel
import com.project.notesapp.model.userRequestModel.UserLoginRequest
import com.project.notesapp.model.userRequestModel.UserRegisterRequest
import com.project.notesapp.model.userResponseModel.UserLoginResponse
import com.project.notesapp.model.userResponseModel.UserRegisterResponse
import com.project.notesapp.repository.UserRepo
import com.project.notesapp.utils.Helper
import com.project.notesapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepo: UserRepo) : ViewModel() {

    val userResponseLiveData: LiveData<NetworkResult<UserRegisterResponse>> get() = userRepo.userResponseLiveData
    val userLoginResponseData: LiveData<NetworkResult<UserLoginResponse>> get() = userRepo.userLoginResponseData


    suspend fun checkUserExists(userName: String): Int = userRepo.isExists(userName)

    fun register(authModel: AuthModel) {
        viewModelScope.launch {
            userRepo.insertUser(authModel)
        }
    }

    fun registerUser(userRequest: UserRegisterRequest) {
        viewModelScope.launch {
            userRepo.createUser(userRequest)
        }
    }

    val getUser: LiveData<List<AuthModel>>
        get() = userRepo.getUser.flowOn(Dispatchers.Main)
            .asLiveData(context = viewModelScope.coroutineContext)

    suspend fun getUserLogin(userName: String, password: String): List<AuthModel> =
        userRepo.getUserLogin(userName, password)

    fun getLoginUser(userLoginRequest: UserLoginRequest) {
        viewModelScope.launch {
            userRepo.loginUser(userLoginRequest)
        }
    }

    fun getUserId() = userRepo.getUserId()

    fun setUserId(userId: String) = userRepo.setUserId(userId)

    fun getUserName() = userRepo.getUserName()

    fun setUserName(userName: String) = userRepo.setUserName(userName)

    fun getUserEmail() = userRepo.getUserEmail()

    fun setUserEmail(userEmail: String) = userRepo.setUserEmail(userEmail)

    fun getLoginEmail() = userRepo.getLoginEmail()

    fun setLoginEmail(loginEmail: String) = userRepo.setLoginEmail(loginEmail)

    fun getUserPhone() = userRepo.getUserPhone()

    fun setUserPhone(userPhone: String) = userRepo.setUserPhone(userPhone)

    fun getDBGenerateId() = userRepo.getDBGenerateId()

    fun setDBGenerateId(dbGenerateId: String) = userRepo.setDBGenerateId(dbGenerateId)

    fun validateRegister(
        name: String,
        phone: String,
        email: String,
        password: String,
        confirmPass: String,
        isLogin: Boolean
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if ((!isLogin && TextUtils.isEmpty(name)) || (!isLogin && TextUtils.isEmpty(phone)) || TextUtils.isEmpty(
                email
            ) || TextUtils.isEmpty(
                password
            ) || (!isLogin && TextUtils.isEmpty(confirmPass))
        ) {
            result = Pair(false, "Enter all credentials")
        } else if ((!isLogin && !Helper.isValidPhone(phone))) {
            result = Pair(false, "Enter valid phone number")
        } else if (!Helper.isValidEmail(email)) {
            result = Pair(false, "Enter valid email")
        } else if (!isLogin && password.length < 5) {
            result = Pair(false, "Password length should be greater than 5")
        } else if (!isLogin && password != confirmPass) {
            result = Pair(false, "Check confirm password")
        }
        return result
    }

    fun logOut(activity: Activity, navController: NavController) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Do you want to log out?")
        builder.setTitle("Alert !!")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes") { _, _ ->
            userRepo.clearPreference()
            navController.navigate(R.id.login)
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.cancel()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun getServiceKeyToken(): String {
        var token = ""
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (!it.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration failed", it.exception)
            }
            token = it.result
            Log.d("FCM", "Token: $token")
        }
        return token
    }

}