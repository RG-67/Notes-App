package com.project.notesapp.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.notesapp.model.AuthModel
import com.project.notesapp.model.NoteModel

@Database(entities = [AuthModel::class, NoteModel::class], version = 2)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null
        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "NoteDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}