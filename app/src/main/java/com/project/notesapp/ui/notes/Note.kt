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
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.navigation.NavigationView
import com.project.notesapp.R
import com.project.notesapp.databinding.NoteBinding
import com.project.notesapp.ui.authentication.AuthViewModel
import com.project.notesapp.ui.bin_menu.BinFragment
import com.project.notesapp.ui.note_menu.NotesFragment
import com.project.notesapp.ui.reminder_menu.RemindersFragment
import com.project.notesapp.utils.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Note : Fragment(), ItemClickListener {

    private var _binding: NoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var context: Context
    private val authViewModel by activityViewModels<AuthViewModel>()
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
                    flag = 1
                    fragmentTransaction.replace(R.id.frameLayout, NotesFragment())
                    true
                }

                R.id.reminder -> {
                    flag = 2
                    fragmentTransaction.replace(R.id.frameLayout, RemindersFragment())
                    true
                }

                R.id.bin -> {
                    flag = 3
                    fragmentTransaction.replace(R.id.frameLayout, BinFragment())
                    true
                }

                R.id.logOut -> {
                    authViewModel.logOut(requireActivity(), findNavController())
                    true
                }

                else -> false
            }.also {
                binding.heading.text = setHeading()
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                binding.noteDrawer.closeDrawer(GravityCompat.START)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

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
                        true
                    }

                    R.id.reminder -> {
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
                heading = "Note"
            }

            2 -> {
                heading = "Reminder"
            }

            3 -> {
                heading = "Bin"
            }
        }
        return heading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*override fun onItemClick(
        view: View,
        position: Int,
        noteId: Int,
        noteTitle: String,
        note: String,
        noteBackImage: Int
    ) {
        findNavController().popBackStack()
    }*/

    override fun onItemClick(
        view: View,
        position: Int,
        noteDatabaseId: String,
        noteId: String,
        title: String,
        note: String,
        from: String
    ) {

    }

}