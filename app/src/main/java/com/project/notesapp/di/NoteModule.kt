package com.project.notesapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.project.notesapp.dao.NoteDao
import com.project.notesapp.dao.NoteDatabase
import com.project.notesapp.dao.UserDao
import com.project.notesapp.repository.NoteRepo
import com.project.notesapp.repository.UserRepo
import com.project.notesapp.utils.PreferenceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(context, NoteDatabase::class.java, "NoteDatabase")
            .addMigrations(MIGRATION_3_4)
            .build()

    private val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE notes ADD COLUMN reminderDate TEXT")
            db.execSQL("ALTER TABLE notes ADD COLUMN reminderTime TEXT")
        }
    }

    @Provides
    fun provideUserDao(noteDatabase: NoteDatabase): UserDao = noteDatabase.userDao()

    @Provides
    fun provideUserRepo(userDao: UserDao, preferenceHelper: PreferenceHelper): UserRepo =
        UserRepo(preferenceHelper, userDao)

    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao = noteDatabase.noteDao()

    @Provides
    fun provideNoteRepo(noteDao: NoteDao): NoteRepo = NoteRepo(noteDao)

}