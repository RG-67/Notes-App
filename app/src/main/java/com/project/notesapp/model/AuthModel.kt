package com.project.notesapp.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authentication")
data class AuthModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val userName: String,
    val password: String
)