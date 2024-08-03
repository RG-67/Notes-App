package com.project.notesapp.ui.note_menu

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.project.notesapp.R
import com.project.notesapp.databinding.FragmentNotesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel by activityViewModels<NoteViewModel>()
    private var context: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        binding.fabBtn.setImageResource(R.drawable.add)
        binding.fabBtn.imageTintList =
            ColorStateList.valueOf(Color.WHITE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabBtn.setOnClickListener {
            binding.noteListRel.visibility = View.GONE
            binding.addNoteRel.visibility = View.VISIBLE
        }
        binding.cancelBtn.setOnClickListener {
            binding.addNoteRel.visibility = View.GONE
            binding.noteListRel.visibility = View.VISIBLE
        }
        binding.saveBtn.setOnClickListener {
            val getValidation = getValidation()
            if (getValidation.second) {

            } else {
                Toast.makeText(context, getValidation.first, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getValidation(): Pair<String, Boolean> {
        val title = binding.title.text.toString()
        val note = binding.note.text.toString()
        return noteViewModel.validateNoteData(title, note)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}