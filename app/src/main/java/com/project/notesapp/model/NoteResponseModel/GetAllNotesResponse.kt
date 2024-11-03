package com.project.notesapp.model.NoteResponseModel

data class GetAllNotesResponse(
    val `data`: List<DataXXXX>,
    val msg: String,
    val status: Boolean
)