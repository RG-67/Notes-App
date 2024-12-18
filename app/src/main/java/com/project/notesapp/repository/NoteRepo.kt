package com.project.notesapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.notesapp.api.NoteApi
import com.project.notesapp.dao.NoteDao
import com.project.notesapp.model.NoteModel
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
import com.project.notesapp.model.NoteResponseModel.ReminderNoteResponse
import com.project.notesapp.model.NoteResponseModel.RestoreBinResponse
import com.project.notesapp.model.NoteResponseModel.SetAndRestoreResponse
import com.project.notesapp.utils.Constants
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
    private val _noteGetAllNoteLiveData = MutableLiveData<NetworkResult<GetAllNotesResponse>>()
    private val _noteGetBinNoteLiveData = MutableLiveData<NetworkResult<GetBinNoteResponse>>()
    private val _noteGetSingleNoteLiveData = MutableLiveData<NetworkResult<ReadNoteResponse>>()
    private val _noteUpdateNoteLiveData = MutableLiveData<NetworkResult<NoteUpdateResponse>>()
    private val _noteDeleteNoteLiveData = MutableLiveData<NetworkResult<DeleteNoteResponse>>()
    private val _noteSetAndRestoreLiveData = MutableLiveData<NetworkResult<SetAndRestoreResponse>>()
    private val _noteRestoreBinLiveData = MutableLiveData<NetworkResult<RestoreBinResponse>>()
    private val _noteReminderNoteLiveData = MutableLiveData<NetworkResult<ReminderNoteResponse>>()
    val noteResponseLiveData: LiveData<NetworkResult<CreateNoteResponse>> get() = _noteResponseLiveData
    val getAllNotesLiveData: LiveData<NetworkResult<GetAllNotesResponse>> get() = _noteGetAllNoteLiveData
    val getBinNotesLiveData: LiveData<NetworkResult<GetBinNoteResponse>> get() = _noteGetBinNoteLiveData
    val getSingleNoteLiveData: LiveData<NetworkResult<ReadNoteResponse>> get() = _noteGetSingleNoteLiveData
    val updateNoteLiveData: LiveData<NetworkResult<NoteUpdateResponse>> get() = _noteUpdateNoteLiveData
    val deleteNoteLiveData: LiveData<NetworkResult<DeleteNoteResponse>> get() = _noteDeleteNoteLiveData
    val setAndRestoreLiveData: LiveData<NetworkResult<SetAndRestoreResponse>> get() = _noteSetAndRestoreLiveData
    val restoreBinLiveData: LiveData<NetworkResult<RestoreBinResponse>> get() = _noteRestoreBinLiveData
    val reminderNoteLiveData: LiveData<NetworkResult<ReminderNoteResponse>> get() = _noteReminderNoteLiveData

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

    suspend fun getAllNotes(getAllNotesRequest: GetAllNotesRequest) {
        try {
            _noteGetAllNoteLiveData.postValue(NetworkResult.Loading())
            val response = noteApi.getAlNotes(getAllNotesRequest)
            handleGetAllNotesResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getSingleNote(readNote: ReadNote) {
        try {
            _noteGetSingleNoteLiveData.postValue(NetworkResult.Loading())
            val response = noteApi.readNote(readNote)
            handleGetSingleNoteResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateNote(updateNoteRequest: UpdateNoteRequest) {
        try {
            _noteUpdateNoteLiveData.postValue(NetworkResult.Loading())
            val response = noteApi.updateNote(updateNoteRequest)
            handleUpdateNote(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun setBinNotes(setAndRestoreRequest: SetAndRestoreRequest) {
        try {
            _noteUpdateNoteLiveData.postValue(NetworkResult.Loading())
            val response = noteApi.setBinNotes(setAndRestoreRequest)
            handleSetAndRestoreNote(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getReminderNote(getAllNotesRequest: GetAllNotesRequest) {
        try {
            _noteReminderNoteLiveData.postValue(NetworkResult.Loading())
            val response = noteApi.getReminderNotes(getAllNotesRequest)
            handleReminderNote(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun handleReminderNote(response: Response<ReminderNoteResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _noteReminderNoteLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _noteReminderNoteLiveData.postValue(NetworkResult.Error(errObj.getString("msg")))
        } else {
            _noteReminderNoteLiveData.postValue(NetworkResult.Error("msg"))
        }
    }

    private fun handleSetAndRestoreNote(response: Response<SetAndRestoreResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _noteSetAndRestoreLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _noteSetAndRestoreLiveData.postValue(NetworkResult.Error(errObj.getString("msg")))
        } else {
            _noteSetAndRestoreLiveData.postValue(NetworkResult.Error("msg"))
        }
    }

    private fun handleRestoreBinNote(response: Response<RestoreBinResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _noteRestoreBinLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _noteRestoreBinLiveData.postValue(NetworkResult.Error(errObj.getString("msg")))
        } else {
            _noteRestoreBinLiveData.postValue(NetworkResult.Error("msg"))
        }
    }

    suspend fun getBinNotes(getAllNotesRequest: GetAllNotesRequest) {
        try {
            _noteGetBinNoteLiveData.postValue(NetworkResult.Loading())
            val response = noteApi.getBinNotes(getAllNotesRequest)
            handleGetBinNoteResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun handleGetBinNoteResponse(response: Response<GetBinNoteResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _noteGetBinNoteLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _noteGetBinNoteLiveData.postValue(NetworkResult.Error(errObj.getString("msg")))
        } else {
            _noteGetBinNoteLiveData.postValue(NetworkResult.Error("msg"))
        }
    }

    suspend fun restoreNote(setAndRestoreRequest: SetAndRestoreRequest) {
        try {
            _noteRestoreBinLiveData.postValue(NetworkResult.Loading())
            val response = noteApi.restoreNote(setAndRestoreRequest)
            handleRestoreBinNote(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteNote(deleteNoteRequest: DeleteNoteRequest) {
        try {
            Log.d("API Request", "URL: ${Constants.DELETE_NOTE}, Body: $deleteNoteRequest")
            _noteDeleteNoteLiveData.postValue(NetworkResult.Loading())
            val response = noteApi.deleteNote(deleteNoteRequest)
            handleDeleteNoteResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*private fun handleDeleteNoteResponse(response: Response<DeleteNoteResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _noteDeleteNoteLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _noteDeleteNoteLiveData.postValue(NetworkResult.Error(errObj.getString("msg")))
        } else {
            _noteDeleteNoteLiveData.postValue(NetworkResult.Error("msg"))
        }
    }*/

    private fun handleDeleteNoteResponse(response: Response<DeleteNoteResponse>) {
        try {
            Log.d("DeleteNoteResponse", "Response: ${response.raw()}")
            if (response.isSuccessful && response.body() != null) {
                _noteDeleteNoteLiveData.postValue(NetworkResult.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val contentType = response.headers()["Content-Type"]
                if (contentType?.contains("application/json") == true) {
                    val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _noteDeleteNoteLiveData.postValue(NetworkResult.Error(errObj.getString("msg")))
                } else {
                    // Handle non-JSON response
                    _noteDeleteNoteLiveData.postValue(NetworkResult.Error("Unexpected server response"))
                }
            } else {
                _noteDeleteNoteLiveData.postValue(NetworkResult.Error("Unknown error occurred"))
            }
        } catch (e: Exception) {
            _noteDeleteNoteLiveData.postValue(NetworkResult.Error("Error: ${e.message}"))
        }
    }

    private fun handleUpdateNote(response: Response<NoteUpdateResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _noteUpdateNoteLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _noteUpdateNoteLiveData.postValue(NetworkResult.Error(errObj.getString("msg")))
        } else {
            _noteUpdateNoteLiveData.postValue(NetworkResult.Error("msg"))
        }
    }

    private fun handleGetSingleNoteResponse(response: Response<ReadNoteResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _noteGetSingleNoteLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _noteGetSingleNoteLiveData.postValue(NetworkResult.Error(errObj.getString("msg")))
        } else {
            _noteGetSingleNoteLiveData.postValue(NetworkResult.Error("msg"))
        }
    }

    private fun handleGetAllNotesResponse(response: Response<GetAllNotesResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _noteGetAllNoteLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _noteGetAllNoteLiveData.postValue(NetworkResult.Error(errObj.getString("msg")))
        } else {
            _noteGetAllNoteLiveData.postValue(NetworkResult.Error("msg"))
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