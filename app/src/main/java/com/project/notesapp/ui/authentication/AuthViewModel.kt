package com.project.notesapp.ui.authentication

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.project.notesapp.model.AuthModel
import com.project.notesapp.repository.UserRepo
import com.project.notesapp.utils.Helper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepo: UserRepo) : ViewModel() {

    suspend fun checkUserExists(userName: String): Int = userRepo.isExists(userName)

    fun register(authModel: AuthModel) {
        viewModelScope.launch {
            userRepo.insertUser(authModel)
        }
    }

    val getUser: LiveData<List<AuthModel>>
        get() = userRepo.getUser.flowOn(Dispatchers.Main)
            .asLiveData(context = viewModelScope.coroutineContext)

    suspend fun getUserLogin(userName: String, password: String): List<AuthModel> =
        userRepo.getUserLogin(userName, password)


    fun validateRegister(
        name: String,
        email: String,
        password: String,
        confirmPass: String,
        isLogin: Boolean
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if ((!isLogin && TextUtils.isEmpty(name)) || TextUtils.isEmpty(email) || TextUtils.isEmpty(
                password
            ) || (!isLogin && TextUtils.isEmpty(confirmPass))
        ) {
            result = Pair(false, "Enter all credentials")
        } else if (!Helper.isValidEmail(email)) {
            result = Pair(false, "Enter valid email")
        } else if (!isLogin && password.length < 5) {
            result = Pair(false, "Password length should be greater than 5")
        } else if (!isLogin && password != confirmPass) {
            result = Pair(false, "Check confirm password")
        }
        return result
    }

}