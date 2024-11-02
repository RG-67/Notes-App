package com.project.notesapp.model.NoteRequestModel

data class ReadNote(
    val databaseUserId: String,
    val noteDatabaseId: String,
    val noteId: String,
    val userId: String
)