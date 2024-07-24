package com.project.notesapp.ui.authentication

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.project.notesapp.model.AuthModel
import com.project.notesapp.repository.UserRepo
import com.project.notesapp.utils.Helper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepo: UserRepo) : ViewModel() {

    fun register(authModel: AuthModel) {
        viewModelScope.launch {
            userRepo.insertUser(authModel)
        }
    }

    val getUser: LiveData<List<AuthModel>>
        get() = userRepo.getUser.flowOn(Dispatchers.Main)
            .asLiveData(context = viewModelScope.coroutineContext)

    fun validateRegister(
        name: String,
        email: String,
        password: String,
        confirmPass: String,
        isLogin: Boolean
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password) || (!isLogin && TextUtils.isEmpty(
                email
            ) || TextUtils.isEmpty(confirmPass))
        ) {
            result = Pair(false, "Enter all credentials")
        } else if (!Helper.isValidEmail(email)) {
            result = Pair(false, "Enter valid email")
        } else if (!TextUtils.isEmpty(password) && password.length < 5) {
            result = Pair(false, "Password length should be greater than 5")
        }
        return result
    }

}