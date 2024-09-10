package com.project.notesapp.ui.reminder_menu

import android.content.ContentValues
import android.content.Context
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
import com.project.notesapp.databinding.FragmentReminderBinding
import com.project.notesapp.model.NoteModel
import com.project.notesapp.ui.authentication.AuthViewModel
import com.project.notesapp.ui.bin_menu.BinAdapter
import com.project.notesapp.ui.note_menu.NoteViewModel
import com.project.notesapp.utils.ItemClickListener
import kotlinx.coroutines.launch

class RemindersFragment : Fragment(), ItemClickListener {

    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel by activityViewModels<NoteViewModel>()
    private var context: Context? = null
    private val authViewModel by activityViewModels<AuthViewModel>()
    private var reminderAdapter: ReminderAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            noteViewModel.getReminderNotes(
                authViewModel.getUserId()?.toInt()!!,
                authViewModel.getUserEmail()!!
            )
                .observe(requireActivity()) {
                    reminderAdapter = ReminderAdapter(it, this@RemindersFragment)
                    val noteList = it as ArrayList<NoteModel>
                    binding.notesRecycler.adapter = reminderAdapter
                    binding.notesRecycler.smoothScrollToPosition(reminderAdapter!!.itemCount)
                    if (noteList.size > 0) {
                        Log.d(ContentValues.TAG, noteList.toString())
                    } else {
                        Toast.makeText(context, "Empty notes", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        binding.backBtn.setOnClickListener {
            binding.noteView.visibility = View.GONE
            binding.notesRecycler.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(
        view: View,
        position: Int,
        noteId: Int,
        noteTitle: String,
        note: String,
        noteBackImage: Int
    ) {
        binding.notesRecycler.visibility = View.GONE
        binding.noteView.visibility = View.VISIBLE
        binding.title.text = noteTitle
        binding.note.text = note
    }


}