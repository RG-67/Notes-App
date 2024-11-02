package com.project.notesapp.model.NoteRequestModel

data class DeleteNoteRequest(
    val databaseUserId: String,
    val noteDatabaseId: String,
    val noteId: String,
    val userId: String
)