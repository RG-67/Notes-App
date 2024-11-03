package com.project.notesapp.utils

import android.view.View

interface ItemClickListener {
//    fun onItemClick(view: View, position: Int, noteId: Int, noteTitle: String, note: String, noteBackImage: Int)
    fun onItemClick(view: View, position: Int, noteDatabaseId: String, noteId: String, title: String, note: String, from: String)
}