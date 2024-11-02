package com.project.notesapp.ui.note_menu

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.project.notesapp.R
import com.project.notesapp.model.AlarmReceiver
import com.project.notesapp.model.AlarmService
import com.project.notesapp.model.NoteModel
import com.project.notesapp.model.NoteRequestModel.CreateNoteRequest
import com.project.notesapp.model.NoteResponseModel.CreateNoteResponse
import com.project.notesapp.repository.NoteRepo
import com.project.notesapp.utils.Helper
import com.project.notesapp.utils.ItemClickListener
import com.project.notesapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepo: NoteRepo) : ViewModel() {

    val noteCreateResponseLiveData: LiveData<NetworkResult<CreateNoteResponse>> get() = noteRepo.noteResponseLiveData

    suspend fun insertNoteData(noteModel: NoteModel) {
        viewModelScope.launch {
            noteRepo.insertNoteData(noteModel)
        }
    }

    suspend fun createNote(createNoteRequest: CreateNoteRequest) {
        viewModelScope.launch {
            noteRepo.createNote(createNoteRequest)
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
        noteId: Int,
        noteBackImage: Int,
        reminderDate: String,
        reminderTime: String
    ) {
        viewModelScope.launch {
            noteRepo.updateNotes(
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
        viewModelScope.launch {
            noteRepo.updateIsDelete(isDelete, userId, noteId)
        }
    }

    suspend fun getBinNotes(userId: Int, userEmail: String): LiveData<List<NoteModel>> {
        return noteRepo.getBinNotes(userId, userEmail).flowOn(Dispatchers.Main)
            .asLiveData(context = coroutineContext)
    }

    suspend fun deleteNote(noteId: Int, userId: Int) {
        viewModelScope.launch {
            noteRepo.deleteNote(noteId, userId)
        }
    }

    suspend fun restoreBinNote(isDelete: Int, userId: Int, noteId: Int) {
        viewModelScope.launch {
            noteRepo.restoreBinNote(isDelete, userId, noteId)
        }
    }

    suspend fun getReminderNotes(userId: Int, userEmail: String): LiveData<List<NoteModel>> {
        return noteRepo.getReminderNotes(userId, userEmail).flowOn(Dispatchers.Main)
            .asLiveData(context = coroutineContext)
    }

    fun validateNoteData(
        title: String,
        note: String,
    ): Pair<String, Boolean> {
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
        return when (viewId) {
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

    fun getDateTime(context: Context, view: View, itemClickListener: ItemClickListener) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, calendarYear, calendarMonth, calendarDay ->
                TimePickerDialog(context, { _, calendarHour, calendarMinute ->
                    if (canScheduleAlarm(context)) {
                        val date = "$calendarDay-$calendarMonth-$calendarYear"
                        val time = "$calendarHour:$calendarMinute"
                        calendar.set(
                            calendarYear,
                            calendarMonth,
                            calendarDay,
                            calendarHour,
                            calendarMinute
                        )
                        setAlarm(
                            context,
                            calendar,
                            date,
                            Helper.convertTimeTo12Hour(time)!!,
                            itemClickListener,
                            view
                        )
                    } else {
                        if (Helper.getSdkVersionCheckForAlarm()) requestExactAlarmPermission(
                            context
                        )
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun canScheduleAlarm(context: Context): Boolean {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        return if (Helper.getSdkVersionCheckForAlarm()) alarmManager.canScheduleExactAlarms()
        else true
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun requestExactAlarmPermission(context: Context) {
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
        intent.data = Uri.parse("package: ${context.packageName}")
        context.startActivity(intent)
    }

    private fun setAlarm(
        context: Context,
        calendar: Calendar,
        date: String,
        time: String,
        itemClickListener: ItemClickListener,
        view: View
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        try {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            Toast.makeText(context, "Reminder set successfully", Toast.LENGTH_SHORT).show()
            itemClickListener.onItemClick(
                view, 0, 0,
                "$date#$time", "dateTimeReminder", 0
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Permission required", Toast.LENGTH_SHORT).show()
        }
    }

}