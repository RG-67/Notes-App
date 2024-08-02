package com.project.notesapp.repository

import com.project.notesapp.dao.NoteDao
import com.project.notesapp.model.NoteModel
import com.project.notesapp.utils.PreferenceHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepo @Inject constructor(
    private val noteDao: NoteDao
) {

    suspend fun insertNoteData(noteModel: NoteModel) = withContext(Dispatchers.IO) {
        noteDao.insertNoteData(noteModel)
    }

}