package com.project.notesapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.notesapp.api.NoteApi
import com.project.notesapp.dao.NoteDao
import com.project.notesapp.model.NoteModel
import com.project.notesapp.model.NoteRequestModel.CreateNoteRequest
import com.project.notesapp.model.NoteResponseModel.CreateNoteResponse
import com.project.notesapp.model.NoteResponseModel.NoteUpdateResponse
import com.project.notesapp.utils.NetworkResult
import com.project.notesapp.utils.PreferenceHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NoteRepo @Inject constructor(
    private val noteDao: NoteDao,
    private val noteApi: NoteApi
) {

    private val _noteResponseLiveData = MutableLiveData<NetworkResult<CreateNoteResponse>>()
    val noteResponseLiveData: LiveData<NetworkResult<CreateNoteResponse>> get() = _noteResponseLiveData

    suspend fun insertNoteData(noteModel: NoteModel) = withContext(Dispatchers.IO) {
        noteDao.insertNoteData(noteModel)
    }

    suspend fun getNotes(userId: Int, userEmail: String): Flow<List<NoteModel>> =
        withContext(Dispatchers.IO) {
            noteDao.getNotes(userId, userEmail)
        }

    suspend fun updateNotes(
        noteDate: String,
        noteTime: String,
        noteTitle: String,
        note: String,
        userId: Int,
        noteId: Int,
        noteBackImage: Int,
        reminderDate: String,
        reminderTime: String
    ) {
        withContext(Dispatchers.IO) {
            noteDao.updateNote(
                noteDate,
                noteTime,
                noteTitle,
                note,
                userId,
                noteId,
                noteBackImage,
                reminderDate,
                reminderTime
            )
        }
    }

    suspend fun updateIsDelete(isDelete: Int, userId: Int, noteId: Int) {
        withContext(Dispatchers.IO) {
            noteDao.updateIsDelete(isDelete, userId, noteId)
        }
    }

    suspend fun getBinNotes(userId: Int, userEmail: String): Flow<List<NoteModel>> =
        withContext(Dispatchers.IO) {
            noteDao.getBinNotes(userId, userEmail)
        }

    suspend fun deleteNote(noteId: Int, userId: Int) {
        withContext(Dispatchers.IO) {
            noteDao.deleteNote(noteId, userId)
        }
    }

    suspend fun restoreBinNote(isDelete: Int, userId: Int, noteId: Int) {
        withContext(Dispatchers.IO) {
            noteDao.restoreBinNote(isDelete, userId, noteId)
        }
    }

    suspend fun getReminderNotes(userId: Int, userEmail: String): Flow<List<NoteModel>> =
        withContext(Dispatchers.IO) {
            noteDao.getReminderNotes(userId, userEmail)
        }

    /* set note api */
    suspend fun createNote(createNoteRequest: CreateNoteRequest) {
        try {
            _noteResponseLiveData.postValue(NetworkResult.Loading())
            val response = noteApi.createNote(createNoteRequest)
            handleCreateResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun handleCreateResponse(response: Response<CreateNoteResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _noteResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _noteResponseLiveData.postValue(NetworkResult.Error(errObj.getString("msg")))
        } else {
            _noteResponseLiveData.postValue(NetworkResult.Error("msg"))
        }
    }


}