package com.project.notesapp.di

import android.content.Context
import androidx.room.Room
import com.project.notesapp.dao.NoteDao
import com.project.notesapp.dao.NoteDatabase
import com.project.notesapp.dao.UserDao
import com.project.notesapp.repository.NoteRepo
import com.project.notesapp.repository.UserRepo
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
            .allowMainThreadQueries().build()

    @Provides
    fun provideUserDao(noteDatabase: NoteDatabase): UserDao = noteDatabase.userDao()

    @Provides
    fun provideUserRepo(userDao: UserDao): UserRepo = UserRepo(userDao)

    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao = noteDatabase.noteDao()

    @Provides
    fun provideNoteRepo(noteDao: NoteDao): NoteRepo = NoteRepo(noteDao)

}