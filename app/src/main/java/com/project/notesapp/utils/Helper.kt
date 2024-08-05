package com.project.notesapp.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

class Helper {

    companion object {

        fun isValidEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
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

        fun getDate(): String {
            val currentDate = Calendar.getInstance().time
            val simpleDateFormat = SimpleDateFormat("d'st' MMMM, yyyy", Locale.getDefault())
            val dayWithSuffix = getDayWithSuffix(currentDate)
            return simpleDateFormat.format(currentDate).replace("st", dayWithSuffix)
        }

        private fun getDayWithSuffix(date: Date): String {
            val calendar = Calendar.getInstance().apply { time = date }
            val day = calendar.get(Calendar.DAY_OF_MONTH)
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

    }

}