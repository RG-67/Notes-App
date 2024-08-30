package com.project.notesapp.repository

import com.project.notesapp.dao.NoteDao
import com.project.notesapp.model.NoteModel
import com.project.notesapp.utils.PreferenceHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepo @Inject constructor(
    private val noteDao: NoteDao
) {

    suspend fun insertNoteData(noteModel: NoteModel) = withContext(Dispatchers.IO) {
        noteDao.insertNoteData(noteModel)
    }

    suspend fun getNotes(userId: Int, userEmail: String): Flow<List<NoteModel>> =
        withContext(Dispatchers.IO) {
            noteDao.getNotes(userId, userEmail)
        }

    suspend fun updateNotes(
        noteDate: String,
        noteTime: String,
        noteTitle: String,
        note: String,
        userId: Int,
        noteId: Int
    ) {
        withContext(Dispatchers.IO) {
            noteDao.updateNote(noteDate, noteTime, noteTitle, note, userId, noteId)
        }
    }

    suspend fun updateIsDelete(isDelete: Int, userId: Int, noteId: Int) {
        withContext(Dispatchers.IO) {
            noteDao.updateIsDelete(isDelete, userId, noteId)
        }
    }

    suspend fun getBinNotes(userId: Int, userEmail: String): Flow<List<NoteModel>> =
        withContext(Dispatchers.IO) {
            noteDao.getBinNotes(userId, userEmail)
        }

    suspend fun deleteNote(noteId: Int, userId: Int) {
        withContext(Dispatchers.IO) {
            noteDao.deleteNote(noteId, userId)
        }
    }

    suspend fun restoreBinNote(isDelete: Int, userId: Int, noteId: Int) {
        withContext(Dispatchers.IO) {
            noteDao.restoreBinNote(isDelete, userId, noteId)
        }
    }

}