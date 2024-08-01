package com.project.notesapp.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.notesapp.model.AuthModel
import com.project.notesapp.model.NoteModel

@Database(entities = [AuthModel::class, NoteModel::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun noteDao(): NoteDao

}