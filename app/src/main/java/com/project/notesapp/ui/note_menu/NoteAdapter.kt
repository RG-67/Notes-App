package com.project.notesapp.ui.note_menu

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.notesapp.databinding.NoteItemLayoutBinding
import com.project.notesapp.model.NoteModel

class NoteAdapter(
    private val onNoteClicked: (NoteModel) -> Unit,
    private val noteList: List<NoteModel>
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteViewHolder {
        return NoteViewHolder(
            NoteItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
        val note = noteList[position]
        note.let {
            holder.bind(it)
        }
    }

    inner class NoteViewHolder(private val binding: NoteItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(noteModel: NoteModel) {
            binding.date.text = noteModel.noteDate
            binding.time.text = noteModel.noteTime
            binding.title.text = noteModel.noteTitle
            binding.note.text = noteModel.note
            binding.root.setOnClickListener {
                onNoteClicked(noteModel)
            }
        }
    }

    class ComparatorDifUtil : DiffUtil.ItemCallback<NoteModel>() {
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem.noteId == newItem.noteId
        }

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem == newItem
        }
    }

}