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

    @Query("Select * from notes where userId = :userId and userEmail = :userEmail and isDelete = 0")
    fun getNotes(userId: Int, userEmail: String): Flow<List<NoteModel>>

    @Query("Update notes set noteDate = :noteDate, noteTime = :noteTime, noteTitle = :noteTitle, note = :note, noteBackImage = :noteBackImage, reminderDate =:reminderDate, reminderTime =:reminderTime where userId = :userId and noteId = :noteId")
    suspend fun updateNote(
        noteDate: String,
        noteTime: String,
        noteTitle: String,
        note: String,
        userId: Int,
        noteId: Int,
        noteBackImage: Int,
        reminderDate: String,
        reminderTime: String
    )

    @Query("Update notes set isDelete = :isDelete where userId = :userId and noteId = :noteId")
    suspend fun updateIsDelete(isDelete: Int, userId: Int, noteId: Int)

    @Query("Select * from notes where userId = :userId and userEmail = :uerEmail and isDelete = 1")
    fun getBinNotes(userId: Int, uerEmail: String): Flow<List<NoteModel>>

    @Query("Delete from notes where noteId = :noteId and userId =:userId")
    suspend fun deleteNote(noteId: Int, userId: Int)

    @Query("Update notes set isDelete = :isDelete where noteId = :noteId and userId =:userId")
    suspend fun restoreBinNote(isDelete: Int, userId: Int, noteId: Int)

    @Query("Select * from notes where userId =:userId and userEmail = :userEmail and reminderDate != '' and reminderTime != ''")
    fun getReminderNotes(userId: Int, userEmail: String): Flow<List<NoteModel>>

}