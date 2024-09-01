package com.project.notesapp.ui.note_menu

import android.content.ContentValues.TAG
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.notesapp.databinding.NoteItemLayoutBinding
import com.project.notesapp.model.NoteModel
import com.project.notesapp.utils.ItemClickListener
import kotlinx.coroutines.CoroutineScope

class NoteAdapter(
    private var noteList: List<NoteModel>,
    private val itemClickListener: ItemClickListener
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
            holder.bind(it, position)
        }
    }

    inner class NoteViewHolder(private val binding: NoteItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(noteModel: NoteModel, position: Int) {
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
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(
                    it,
                    position,
                    noteModel.noteId,
                    noteModel.noteTitle,
                    noteModel.note,
                    noteModel.noteBackImage
                )
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