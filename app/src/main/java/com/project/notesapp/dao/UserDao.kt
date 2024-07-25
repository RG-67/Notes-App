package com.project.notesapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.notesapp.model.AuthModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    /*@Insert
    suspend fun insertUser(authModel: AuthModel)*/
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(authModel: AuthModel): Int

    @Query("Select * from authentication")
    fun getUser(): Flow<List<AuthModel>>

}