package com.project.notesapp.dao

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.notesapp.model.AuthModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("Select count(*) from authentication where userName = :userName")
    suspend fun checkIsExists(userName: String): Int

    @Insert
    suspend fun insertUser(authModel: AuthModel)

    @Query("Select * from authentication")
    fun getUser(): Flow<List<AuthModel>>

    @Query("Select * from authentication where userName = :userName and password = :password")
    suspend fun getUserLogin(userName: String, password: String): List<AuthModel>

}