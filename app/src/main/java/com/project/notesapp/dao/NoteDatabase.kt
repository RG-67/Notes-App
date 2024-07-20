package com.project.notesapp.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.notesapp.model.AuthModel

@Database(entities = [AuthModel::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}