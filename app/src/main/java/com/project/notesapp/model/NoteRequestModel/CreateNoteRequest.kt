package com.project.notesapp.model.NoteRequestModel

data class CreateNoteRequest(
    val databaseUserId: String,
    val note: String,
    val title: String,
    val userId: String,
    val reminderDateTime: String
)