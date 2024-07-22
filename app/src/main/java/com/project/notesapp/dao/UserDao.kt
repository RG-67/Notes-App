package com.project.notesapp.dao

import androidx.room.Dao
import androidx.room.Insert
import com.project.notesapp.model.AuthModel

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(authModel: AuthModel)

}