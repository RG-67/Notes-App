package com.project.notesapp.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.project.notesapp.utils.Helper

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d("AlarmReceiver ==>", "Alarm received")
        val serviceIntent = Intent(p0, AlarmService::class.java)
        if (Helper.getSdkVersionCheck()) p0?.startForegroundService(serviceIntent)
        else p0?.startService(serviceIntent)
    }

}