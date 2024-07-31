package com.project.notesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    val noteId: Int,
    val userId: Int,
    val userName: String,
    val userEmail: String,
    val noteTitle: String,
    val note: String
)