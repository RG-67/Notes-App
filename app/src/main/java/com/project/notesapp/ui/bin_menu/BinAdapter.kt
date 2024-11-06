package com.project.notesapp.ui.bin_menu

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.notesapp.databinding.NoteItemLayoutBinding
import com.project.notesapp.model.NoteModel
import com.project.notesapp.model.NoteResponseModel.GetAllNotesResponse
import com.project.notesapp.model.NoteResponseModel.GetBinNoteResponse
import com.project.notesapp.utils.ItemClickListener

class BinAdapter(
//    private val noteList: List<NoteModel>,
    private val notesResponse: GetBinNoteResponse,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<BinAdapter.BinViewHolder>() {

    inner class BinViewHolder(private val binding: NoteItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(getAllNotesResponse: GetBinNoteResponse, position: Int) {
            /*if (noteModel.noteBackImage != 0) {
                binding.noteBackImg.setImageResource(noteModel.noteBackImage)
                binding.title.setTextColor(Color.WHITE)
                binding.note.setTextColor(Color.WHITE)
            } else {
                binding.title.setTextColor(Color.BLACK)
                binding.note.setTextColor(Color.BLACK)
            }*/
            val data = getAllNotesResponse.data[position]
            binding.date.text = data.date.toString()
            binding.time.text = data.time
            binding.title.text = data.title
            binding.note.text = data.note
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(
                    it,
                    position,
                    data._id,
                    data.noteId,
                    data.title,
                    data.note,
                    "binAdapter"
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
        val note = notesResponse.data[position]
        note.let {
            holder.bind(notesResponse, position)
        }
    }

    override fun getItemCount(): Int = notesResponse.data.size


}