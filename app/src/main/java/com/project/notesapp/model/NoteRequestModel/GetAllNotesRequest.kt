package com.project.notesapp.model.NoteRequestModel

data class GetAllNotesRequest(
    val databaseUserId: String,
    val userId: String
)