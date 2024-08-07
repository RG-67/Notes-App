package com.project.notesapp.utils

import android.view.View

interface ItemClickListener {
    fun onItemClick(view: View, position: Int, noteId: Int)
}