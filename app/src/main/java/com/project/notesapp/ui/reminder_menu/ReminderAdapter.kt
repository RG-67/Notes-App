package com.project.notesapp.ui.reminder_menu

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.project.notesapp.databinding.NoteItemLayoutBinding
import com.project.notesapp.databinding.ReminderItemLayoutBinding
import com.project.notesapp.model.NoteModel
import com.project.notesapp.model.NoteResponseModel.DataXXXXXXXX
import com.project.notesapp.model.NoteResponseModel.ReminderNoteResponse
import com.project.notesapp.ui.bin_menu.BinAdapter
import com.project.notesapp.utils.Helper
import com.project.notesapp.utils.ItemClickListener

class ReminderAdapter(
    private val noteList: ReminderNoteResponse,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    inner class ReminderViewHolder(private val binding: ReminderItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        /*fun bind(noteModel: NoteModel, position: Int) {
            if (noteModel.noteBackImage != 0) {
                binding.noteBackImg.setImageResource(noteModel.noteBackImage)
                binding.title.setTextColor(Color.WHITE)
                binding.note.setTextColor(Color.WHITE)
            } else {
                binding.title.setTextColor(Color.BLACK)
                binding.note.setTextColor(Color.BLACK)
            }
            binding.date.text = noteModel.noteDate
            binding.time.text = noteModel.noteTime
            binding.title.text = noteModel.noteTitle
            binding.note.text = noteModel.note
            binding.reminderDate.text = noteModel.reminderDate
            binding.reminderTime.text = noteModel.reminderTime
            *//*binding.root.setOnClickListener {
                itemClickListener.onItemClick(
                    it,
                    position,
                    noteModel.noteId,
                    noteModel.noteTitle,
                    noteModel.note,
                    noteModel.noteBackImage
                )
            }*//*
        }*/
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(it: DataXXXXXXXX, position: Int) {
            val date = Helper.getDateTime(it.reminderDateTime).first
            val time = Helper.getDateTime(it.reminderDateTime).second
            binding.date.text = Helper.getNoteCreateDate(it.date.toString())
            binding.time.text = Helper.convertTimeTo12Hour(it.time)
            binding.title.text = it.title
            binding.note.text = it.note
            binding.reminderDate.text = date
            binding.reminderTime.text = time
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReminderAdapter.ReminderViewHolder {
        return ReminderViewHolder(
            ReminderItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ReminderAdapter.ReminderViewHolder, position: Int) {
        val note = noteList.data[position]
        note.let {
            holder.bind(it, position)
        }
    }

    override fun getItemCount(): Int = noteList.data.size
}