package com.project.notesapp.api

import com.project.notesapp.model.NoteRequestModel.CreateNoteRequest
import com.project.notesapp.model.NoteRequestModel.DeleteNoteRequest
import com.project.notesapp.model.NoteRequestModel.GetAllNotesRequest
import com.project.notesapp.model.NoteRequestModel.ReadNote
import com.project.notesapp.model.NoteRequestModel.SetAndRestoreRequest
import com.project.notesapp.model.NoteRequestModel.UpdateNoteRequest
import com.project.notesapp.model.NoteResponseModel.CreateNoteResponse
import com.project.notesapp.model.NoteResponseModel.DeleteNoteResponse
import com.project.notesapp.model.NoteResponseModel.GetAllNotesResponse
import com.project.notesapp.model.NoteResponseModel.GetBinNoteResponse
import com.project.notesapp.model.NoteResponseModel.NoteUpdateResponse
import com.project.notesapp.model.NoteResponseModel.ReadNoteResponse
import com.project.notesapp.model.NoteResponseModel.SetAndRestoreResponse
import com.project.notesapp.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface NoteApi {

    @POST(Constants.CREATE_NOTE)
    suspend fun createNote(@Body createNoteRequest: CreateNoteRequest): Response<CreateNoteResponse>

    @POST(Constants.GET_ALL_NOTES)
    suspend fun getAlNotes(@Body getAllNotesRequest: GetAllNotesRequest): Response<GetAllNotesResponse>

    @POST(Constants.READ_NOTE)
    suspend fun readNote(@Body readNoteRequest: ReadNote): Response<ReadNoteResponse>

    @PATCH(Constants.UPDATE_NOTE)
    suspend fun updateNote(@Body updateNoteRequest: UpdateNoteRequest): Response<NoteUpdateResponse>

    @POST(Constants.DELETE_NOTE)
    suspend fun deleteNote(@Body deleteNoteRequest: DeleteNoteRequest): Response<DeleteNoteResponse>

    @PATCH(Constants.SET_BIN_NOTES)
    suspend fun setBinNotes(@Body setAndRestoreRequest: SetAndRestoreRequest): Response<SetAndRestoreResponse>

    @POST(Constants.GET_BIN_NOTES)
    suspend fun getBinNotes(@Body getAllNotesRequest: GetAllNotesRequest): Response<GetBinNoteResponse>

    @PATCH(Constants.RESTORE_NOTE)
    suspend fun restoreNote(@Body setAndRestoreRequest: SetAndRestoreRequest): Response<SetAndRestoreResponse>

}