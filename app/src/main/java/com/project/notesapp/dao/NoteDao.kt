package com.project.notesapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.project.notesapp.model.NoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNoteData(noteModel: NoteModel)

    @Query("Select * from notes where userId = :userId and userEmail = :userEmail")
    fun getNotes(userId: Int, userEmail: String): Flow<List<NoteModel>>

}