package com.project.notesapp.ui.authentication

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.project.notesapp.R
import com.project.notesapp.model.AuthModel
import com.project.notesapp.repository.UserRepo
import com.project.notesapp.ui.note_menu.NotesFragment
import com.project.notesapp.ui.notes.Note
import com.project.notesapp.utils.Helper
import com.project.notesapp.utils.ItemClickListener
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

    fun getUserId() = userRepo.getUserId()

    fun setUserId(userId: String) = userRepo.setUserId(userId)

    fun getUserName() = userRepo.getUserName()

    fun setUserName(userName: String) = userRepo.setUserName(userName)

    fun getUserEmail() = userRepo.getUserEmail()

    fun setUserEmail(userEmail: String) = userRepo.setUserEmail(userEmail)

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

    fun logOut(activity: Activity, navController: NavController) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Do you want to log out?")
        builder.setTitle("Alert !!")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes") { _, _ ->
            userRepo.clearPreference()
            navController.popBackStack()
//            navController.navigate(R.id.login)
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.cancel()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}