package com.project.notesapp.model.NoteResponseModel

data class NoteUpdateResponse(
    val `data`: Data,
    val msg: String,
    val status: Boolean
)