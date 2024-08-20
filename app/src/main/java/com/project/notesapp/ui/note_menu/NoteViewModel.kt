package com.project.notesapp.ui.note_menu

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.RadioButton
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
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

    fun getTypeface(viewId: Int, context: Context): Typeface? {
        return when(viewId) {
            R.id.sansDef -> Typeface.SANS_SERIF
            R.id.sansDef -> Typeface.SANS_SERIF
            R.id.sansBlack -> Typeface.create("sans-serif-black", Typeface.NORMAL)
            R.id.cardoReg -> ResourcesCompat.getFont(context, R.font.cardo_regular)
            R.id.cardoBold -> ResourcesCompat.getFont(context, R.font.cardo_bold)
            R.id.cardoItalic -> ResourcesCompat.getFont(context, R.font.cardo_italic)
            R.id.fanwoodReg -> ResourcesCompat.getFont(
                context,
                R.font.fanwoodtext_regular
            )
            R.id.fanwoodItalic -> ResourcesCompat.getFont(
                context,
                R.font.fanwoodtext_italic
            )
            R.id.honkReg -> ResourcesCompat.getFont(context, R.font.honk_regular)
            R.id.notoColorReg -> ResourcesCompat.getFont(
                context,
                R.font.notocoloremoji_regular
            )
            R.id.poppinsReg -> ResourcesCompat.getFont(context, R.font.poppins_regular)
            R.id.poppinsMedium -> ResourcesCompat.getFont(
                context,
                R.font.poppins_medium
            )
            R.id.poppinsSemibold -> ResourcesCompat.getFont(
                context,
                R.font.poppins_semibold
            )
            R.id.poppinsItalic -> ResourcesCompat.getFont(
                context,
                R.font.poppins_italic
            )
            R.id.robotoReg -> ResourcesCompat.getFont(context, R.font.roboto_regular)
            R.id.robotoMedium -> ResourcesCompat.getFont(context, R.font.roboto_medium)
            R.id.robotoItalic -> ResourcesCompat.getFont(context, R.font.roboto_italic)
            R.id.titilliReg -> ResourcesCompat.getFont(
                context,
                R.font.titilliumweb_regular
            )
            R.id.titilliSemibold -> ResourcesCompat.getFont(
                context,
                R.font.titilliumweb_semibold
            )
            R.id.titilliBold -> ResourcesCompat.getFont(
                context,
                R.font.titilliumweb_bold
            )
            R.id.titilliItalic -> ResourcesCompat.getFont(
                context,
                R.font.titilliumweb_italic
            )
            else -> Typeface.DEFAULT
        }
    }

}