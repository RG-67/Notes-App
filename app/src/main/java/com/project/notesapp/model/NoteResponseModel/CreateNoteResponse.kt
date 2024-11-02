package com.project.notesapp.model.NoteResponseModel

data class CreateNoteResponse(
    val `data`: DataX,
    val msg: String,
    val status: Boolean
)