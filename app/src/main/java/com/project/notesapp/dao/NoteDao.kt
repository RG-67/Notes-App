package com.project.notesapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.project.notesapp.model.NoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNoteData(noteModel: NoteModel)

    @Query("Select * from notes where userId = :userId and userEmail = :userEmail")
    fun getNotes(userId: Int, userEmail: String): Flow<List<NoteModel>>

    @Query("Update notes set noteDate = :noteDate, noteTime = :noteTime, noteTitle = :noteTitle, note = :note where userId = :userId and noteId = :noteId")
    suspend fun updateNote(
        noteDate: String,
        noteTime: String,
        noteTitle: String,
        note: String,
        userId: Int,
        noteId: Int
    )

    @Query("Delete from notes where noteId = :noteId and userId =:userId")
    suspend fun deleteNote(noteId: Int, userId: Int)

}