package com.project.notesapp.ui.bin_menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.notesapp.databinding.NoteItemLayoutBinding
import com.project.notesapp.model.NoteModel

class BinAdapter(
    private val noteList: List<NoteModel>
) : RecyclerView.Adapter<BinAdapter.BinViewHolder>() {

    inner class BinViewHolder(private val binding: NoteItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(noteModel: NoteModel, position: Int) {

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

        }
    }

    override fun getItemCount(): Int = noteList.size


}