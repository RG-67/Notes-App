package com.project.notesapp.model.NoteRequestModel

data class UpdateNoteRequest(
    val databaseUserId: String,
    val note: String,
    val noteDatabaseId: String,
    val noteId: String,
    val title: String,
    val userId: String
)