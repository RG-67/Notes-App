package com.project.notesapp.ui.bin_menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.notesapp.databinding.NoteItemLayoutBinding
import com.project.notesapp.model.NoteModel
import com.project.notesapp.utils.ItemClickListener

class BinAdapter(
    private val noteList: List<NoteModel>,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<BinAdapter.BinViewHolder>() {

    inner class BinViewHolder(private val binding: NoteItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(noteModel: NoteModel, position: Int) {
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
                    noteModel.note
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinAdapter.BinViewHolder {
        return BinViewHolder(
            NoteItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BinAdapter.BinViewHolder, position: Int) {
        val note = noteList[position]
        note.let {
            holder.bind(it, position)
        }
    }

    override fun getItemCount(): Int = noteList.size


}