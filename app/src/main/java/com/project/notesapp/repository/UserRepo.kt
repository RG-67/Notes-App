package com.project.notesapp.repository

import androidx.annotation.WorkerThread
import com.project.notesapp.dao.UserDao
import com.project.notesapp.model.AuthModel
import com.project.notesapp.ui.authentication.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepo @Inject constructor(private val userDao: UserDao) {


    suspend fun insertUser(authModel: AuthModel) = withContext(Dispatchers.IO) {
        userDao.insertUser(authModel)
    }

    val getUser: Flow<List<AuthModel>> = userDao.getUser()

}