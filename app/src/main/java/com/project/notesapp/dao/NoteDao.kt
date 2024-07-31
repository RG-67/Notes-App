package com.project.notesapp.dao

import androidx.room.Dao
import androidx.room.Insert
import com.project.notesapp.model.NoteModel

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNoteData(noteModel: NoteModel)

}