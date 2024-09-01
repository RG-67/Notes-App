package com.project.notesapp.model

import android.graphics.drawable.Drawable
import android.media.Image
import androidx.room.Delete
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
    val note: String,
    val noteDate: String,
    val noteTime: String,
    val isDelete: Int,
    val noteBackImage: Int
)