package com.project.notesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authentication")
data class AuthModel(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val name: String,
    val userName: String,
    val password: String
)