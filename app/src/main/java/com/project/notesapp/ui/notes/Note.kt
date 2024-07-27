package com.project.notesapp.ui.notes

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.project.notesapp.R
import com.project.notesapp.databinding.NoteBinding
import com.project.notesapp.ui.note_menu.NotesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Note : Fragment() {

    private var _binding: NoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var context: Context

    private var flag = 1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NoteBinding.inflate(inflater, container, false)

        if (savedInstanceState == null) {
            binding.heading.text = setHeading()
            childFragmentManager.beginTransaction().replace(R.id.frameLayout, NotesFragment())
                .commit()
        }

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            val fragmentTransaction = childFragmentManager.beginTransaction()
            when (menuItem.itemId) {
                R.id.note -> {
                    binding.heading.text = setHeading()
                    fragmentTransaction.replace(R.id.frameLayout, NotesFragment())
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                    true
                }

                else -> false
            }.also {
                binding.noteDrawer.closeDrawer(GravityCompat.START)
            }
        }

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

        ((requireActivity()) as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.note -> {
                        Toast.makeText(context, "Toast", Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.reminder -> {
                        true
                    }

                    R.id.label -> {
                        true
                    }

                    R.id.bin -> {
                        true
                    }

                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setHeading(): String {
        var heading = ""
        when (flag) {
            1 -> {
                heading = "Notes"
            }

            2 -> {
                heading = "Reminder"
            }

            3 -> {
                heading
            }

            4 -> {

            }
        }
        heading = if (flag == 1) {
            "Notes"
        } else if (flag == 2) {
            "Reminder"
        } else if (flag == 3) {
            "Label"
        } else {
            "Bin"
        }
        return heading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}