package com.project.notesapp.api

import com.project.notesapp.model.NoteRequestModel.CreateNoteRequest
import com.project.notesapp.model.NoteResponseModel.CreateNoteResponse
import com.project.notesapp.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NoteApi {

    @POST(Constants.CREATE_NOTE)
    suspend fun createNote(@Body createNoteRequest: CreateNoteRequest): Response<CreateNoteResponse>

}