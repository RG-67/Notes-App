package com.project.notesapp.ui.note_menu

import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.project.notesapp.R
import com.project.notesapp.databinding.FragmentNotesBinding
import com.project.notesapp.model.NoteModel
import com.project.notesapp.ui.authentication.AuthViewModel
import com.project.notesapp.utils.Helper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel by activityViewModels<NoteViewModel>()
    private var context: Context? = null
    private val authViewModel by activityViewModels<AuthViewModel>()
    private var noteAdapter: NoteAdapter? = null

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
        lifecycleScope.launch {
            noteViewModel.getNotes(
                authViewModel.getUserId()?.toInt()!!,
                authViewModel.getUserEmail()!!
            )
                .observe(requireActivity()) {
                    noteAdapter = NoteAdapter(::onNoteClicked, it)
                    val noteList = it as ArrayList<NoteModel>
                    if (noteList.size > 0) {
                        Log.d(TAG, noteList.toString())
                        binding.notesRecycler.adapter = noteAdapter
                    } else {
                        Toast.makeText(context, "Empty notes", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        binding.fabBtn.setOnClickListener {
            showHide()
        }
        binding.cancelBtn.setOnClickListener {
            showHide()
        }
        binding.saveBtn.setOnClickListener {
            val getValidation = getValidation()
            val getNoteData = getNoteData()
            if (getValidation.second) {
                lifecycleScope.launch {
                    Log.d(TAG, getNoteData.toString())
                    noteViewModel.insertNoteData(getNoteData)
                    showHide()
                }
            } else {
                showError(getValidation.first)
            }
        }
    }

    private fun getValidation(): Pair<String, Boolean> {
        val title = binding.title.text.toString()
        val note = binding.note.text.toString()
        return noteViewModel.validateNoteData(title, note)
    }

    private fun getNoteData(): NoteModel {
        return binding.run {
            NoteModel(
                0,
                authViewModel.getUserId()?.toInt()!!,
                authViewModel.getUserName()!!,
                authViewModel.getUserEmail()!!,
                binding.title.text.toString(),
                binding.note.text.toString(),
                Helper.getDate(),
                Helper.getCurrentTime()
            )
        }
    }

    private fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    private fun showHide() {
        if (binding.noteListRel.visibility == View.VISIBLE) {
            binding.noteListRel.visibility = View.GONE
            binding.addNoteRel.visibility = View.VISIBLE
        } else {
            binding.addNoteRel.visibility = View.GONE
            binding.noteListRel.visibility = View.VISIBLE
        }
    }

    private fun onNoteClicked(noteModel: NoteModel) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}