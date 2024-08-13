package com.project.notesapp.ui.note_menu

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.project.notesapp.R
import com.project.notesapp.model.NoteModel
import com.project.notesapp.repository.NoteRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepo: NoteRepo) : ViewModel() {

    suspend fun insertNoteData(noteModel: NoteModel) {
        viewModelScope.launch {
            noteRepo.insertNoteData(noteModel)
        }
    }

    suspend fun getNotes(userId: Int, userEmail: String): LiveData<List<NoteModel>> {
        return noteRepo.getNotes(userId, userEmail).flowOn(Dispatchers.Main)
            .asLiveData(context = coroutineContext)
    }

    suspend fun updateNotes(
        noteDate: String,
        noteTime: String,
        noteTitle: String,
        note: String,
        userId: Int,
        noteId: Int
    ) {
        viewModelScope.launch {
            noteRepo.updateNotes(noteDate, noteTime, noteTitle, note, userId, noteId)
        }
    }

    suspend fun deleteNote(noteId: Int, userId: Int) {
        viewModelScope.launch {
            noteRepo.deleteNote(noteId, userId)
        }
    }

    fun validateNoteData(title: String, note: String): Pair<String, Boolean> {
        var result = Pair("", true)
        if (TextUtils.isEmpty(title)) {
            result = Pair("Enter title", false)
        } else if (TextUtils.isEmpty(note)) {
            result = Pair("Note should not empty", false)
        }
        return result
    }

    fun setPalette(
        type: String,
        radioButtonId: Int
    ): List<Boolean> {
        val drawableList = ArrayList<Boolean>()
        when (type) {
            "palette" -> {
                when (radioButtonId) {
                    R.id.noneRdBtn -> {
                        drawableList.add(true)
                        drawableList.add(false)
                        drawableList.add(false)
                        drawableList.add(false)
                        drawableList.add(false)
                        drawableList.add(false)
                    }

                    R.id.back1 -> {
                        drawableList.add(false)
                        drawableList.add(true)
                        drawableList.add(false)
                        drawableList.add(false)
                        drawableList.add(false)
                        drawableList.add(false)
                    }

                    R.id.back2 -> {
                        drawableList.add(false)
                        drawableList.add(false)
                        drawableList.add(true)
                        drawableList.add(false)
                        drawableList.add(false)
                        drawableList.add(false)
                    }

                    R.id.back3 -> {
                        drawableList.add(false)
                        drawableList.add(false)
                        drawableList.add(false)
                        drawableList.add(true)
                        drawableList.add(false)
                        drawableList.add(false)
                    }

                    R.id.back4 -> {
                        drawableList.add(false)
                        drawableList.add(false)
                        drawableList.add(false)
                        drawableList.add(false)
                        drawableList.add(true)
                        drawableList.add(false)
                    }

                    R.id.back5 -> {
                        drawableList.add(false)
                        drawableList.add(false)
                        drawableList.add(false)
                        drawableList.add(false)
                        drawableList.add(false)
                        drawableList.add(true)
                    }
                }
            }
        }
        return drawableList
    }

}