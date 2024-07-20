package com.project.notesapp.dao

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase
import com.project.notesapp.ui.authentication.AuthViewModel

@Database(entities = [AuthViewModel::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}