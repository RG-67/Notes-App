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
import com.project.notesapp.model.NoteResponseModel.GetAllNotesResponse
import com.project.notesapp.utils.ItemClickListener
import kotlinx.coroutines.CoroutineScope

class NoteAdapter(
//    private var noteList: List<NoteModel>,
    private val notesResponse: GetAllNotesResponse,
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
        return notesResponse.data.size
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
        val note = notesResponse.data[position]
        note.let {
            holder.bind(notesResponse, position)
        }
    }

    inner class NoteViewHolder(private val binding: NoteItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(response: GetAllNotesResponse, position: Int) {
            /*if (noteModel.noteBackImage != 0) {
                binding.noteBackImg.setImageResource(noteModel.noteBackImage)
                binding.title.setTextColor(Color.WHITE)
                binding.note.setTextColor(Color.WHITE)
            } else {
                binding.title.setTextColor(Color.BLACK)
                binding.note.setTextColor(Color.BLACK)
            }*/
            val model = response.data[position]
            binding.date.text = model.date.toString()
            binding.time.text = model.time
            binding.title.text = model.title
            binding.note.text = model.note
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(
                    /*it,
                    position,
                    noteModel.noteId,
                    noteModel.noteTitle,
                    noteModel.note,
                    noteModel.noteBackImage*/
                    it,
                    position,
                    model._id,
                    model.noteId,
                    model.title,
                    model.note,
                    "noteAdapter"
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