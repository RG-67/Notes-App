package com.project.notesapp.repository

import androidx.annotation.WorkerThread
import com.project.notesapp.dao.UserDao
import com.project.notesapp.model.AuthModel
import com.project.notesapp.ui.authentication.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.invoke
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepo @Inject constructor(private val userDao: UserDao) {


    suspend fun isExists(userName: String): Int = userDao.checkIsExists(userName)

    suspend fun insertUser(authModel: AuthModel) = withContext(Dispatchers.IO) {
        userDao.insertUser(authModel)
    }

    val getUser: Flow<List<AuthModel>> = userDao.getUser()

    suspend fun getUserLogin(userName: String, password: String): List<AuthModel> =
        withContext(Dispatchers.IO) {
            userDao.getUserLogin(userName, password)
        }

}