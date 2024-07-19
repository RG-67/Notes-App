package com.project.notesapp.ui.notes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.project.notesapp.R
import com.project.notesapp.databinding.NoteBinding

class Note : Fragment() {

    private var _binding: NoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var context: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NoteBinding.inflate(inflater, container, false)

        val actionToggleBar = ActionBarDrawerToggle(
            requireActivity(),
            binding.noteDrawer,
            R.string.open,
            R.string.close
        )
        binding.noteDrawer.addDrawerListener(actionToggleBar)
        actionToggleBar.syncState()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionToggleBar = ActionBarDrawerToggle(
            requireActivity(),
            binding.noteDrawer,
            binding.toolBar,
            R.string.open,
            R.string.close
        )
        binding.noteDrawer.addDrawerListener(actionToggleBar)
        actionToggleBar.syncState()
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}