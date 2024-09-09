package com.project.notesapp.model

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import android.view.WindowManager
import androidx.core.app.NotificationCompat
import com.project.notesapp.R
import com.project.notesapp.ui.notes.Note
import com.project.notesapp.utils.Helper

class AlarmService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("AlarmService ==>", "Alarm service start")
        startForeground(1, createNotification())
        val wakeLock = (getSystemService(Context.POWER_SERVICE) as PowerManager).newWakeLock(
            PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE,
            "Note::AlarmService"
        )
        wakeLock.acquire(1 * 60 * 1000L)
//        showDialog()
        return START_NOT_STICKY
    }

    private fun createNotification(): Notification {
        val notificationChannelId = "ALARM_SERVICE_CHANNEL"
        if (Helper.getSdkVersionCheck()) {
            val notificationChannel = NotificationChannel(
                notificationChannelId,
                "Alarm service notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notificationBuilder = NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("Note Reminder")
            .setContentText("Check your note in the reminder")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setPriority(Notification.PRIORITY_HIGH)
        return notificationBuilder.build()
    }

    private fun showDialog() {
        val dialogIntent = Intent(this, Note::class.java)
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(dialogIntent)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}