package com.project.notesapp.utils

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class Helper {

    companion object {

        fun isValidEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidPhone(phone: String): Boolean {
            return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches()
        }

        fun hideKeyboard(view: View) {
            try {
                val inputMethodManager =
                    view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            } catch (e: Exception) {
                Log.d("HideKeyboardException ==>", e.toString())
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getDateTime(dateTime: String): Pair<String, String> {
            val dt = ZonedDateTime.parse(dateTime)
            val day = dt.dayOfMonth
            val dayFormat = getDayWithSuffix(day)
            val monthFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH)
            val month = dt.format(monthFormatter)
            val year = dt.year

            val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)
            val formattedTime = dt.format(timeFormatter)

            val formattedDate = "$dayFormat $month, $year"
            return Pair(formattedDate, formattedTime)
        }

        fun getNoteCreateDate(date: String): String {
            val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH)
            val dateObj = dateFormat.parse(date)
            val dateInString = SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH).format(dateObj!!)
            return dateInString.replaceFirstChar { it.uppercase() }
        }

        private fun getDayWithSuffix(day: Int): String {
            return when {
                day in 11..13 -> "${day}th"
                day % 10 == 1 -> "${day}st"
                day % 10 == 2 -> "${day}nd"
                day % 10 == 3 -> "${day}rd"
                else -> "${day}th"
            }
        }

        fun getCurrentTime(): String {
            val currentTime = Calendar.getInstance().time
            val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            return timeFormat.format(currentTime)
        }

        fun getSdkVersionCheck(): Boolean {
            return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        }

        fun getSdkVersionCheckForAlarm(): Boolean {
            return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        }

        fun convertTimeTo12Hour(time: String): String? {
            return try {
                val inputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
                val currentTime = inputFormat.parse(time)
                outputFormat.format(currentTime!!)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }


    }

}