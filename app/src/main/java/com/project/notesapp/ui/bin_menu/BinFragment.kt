package com.project.notesapp.ui.bin_menu

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.project.notesapp.R
import com.project.notesapp.databinding.FragmentBinBinding
import com.project.notesapp.model.NoteModel
import com.project.notesapp.model.NoteRequestModel.DeleteNoteRequest
import com.project.notesapp.model.NoteRequestModel.GetAllNotesRequest
import com.project.notesapp.model.NoteRequestModel.SetAndRestoreRequest
import com.project.notesapp.model.NoteResponseModel.GetAllNotesResponse
import com.project.notesapp.ui.authentication.AuthViewModel
import com.project.notesapp.ui.note_menu.NoteAdapter
import com.project.notesapp.ui.note_menu.NoteViewModel
import com.project.notesapp.utils.ItemClickListener
import com.project.notesapp.utils.NetworkResult
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BinFragment : Fragment(), ItemClickListener {

    private var _binding: FragmentBinBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel by activityViewModels<NoteViewModel>()
    private var context: Context? = null
    private val authViewModel by activityViewModels<AuthViewModel>()
    private var binAdapter: BinAdapter? = null
    private var balloon: Balloon? = null
    private var restoreBtn: LinearLayout? = null
    private var deleteBinBtn: LinearLayout? = null
    private var balloonBinNoteLayout: View? = null
    private var userNoteId = ""
    private var noteDBId = ""
    private var ntId = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBinBinding.inflate(LayoutInflater.from(context), container, false)

        balloonBinNoteLayout = layoutInflater.inflate(R.layout.balloon_bin_note_layout, null)
        balloon = Balloon.Builder(requireContext())
            .setLayout(balloonBinNoteLayout!!)
            .setArrowSize(10)
            .setArrowOrientation(ArrowOrientation.TOP)
            .setArrowPosition(0.5f)
            .setWidthRatio(0.5f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setCornerRadius(5f)
            .setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            .setArrowColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.transparent_green
                )
            )
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .build()
        restoreBtn = balloon!!.getContentView().findViewById(R.id.restoreBtn)
        deleteBinBtn = balloon!!.getContentView().findViewById(R.id.deleteBinBtn)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            /*noteViewModel.getBinNotes(
                authViewModel.getUserId()?.toInt()!!,
                authViewModel.getUserEmail()!!
            )
                .observe(requireActivity()) {
                    binAdapter = BinAdapter(it, this@BinFragment)
                    val noteList = it as ArrayList<NoteModel>
                    binding.notesRecycler.adapter = binAdapter
                    binding.notesRecycler.smoothScrollToPosition(binAdapter!!.itemCount)
                    if (noteList.size > 0) {
                        Log.d(ContentValues.TAG, noteList.toString())
                    } else {
                        Toast.makeText(context, "Empty notes", Toast.LENGTH_SHORT).show()
                    }
                }*/
        }
        getBinNotes()

        deleteBinBtn?.setOnClickListener {
            balloon?.dismiss()
            lifecycleScope.launch {
//                noteViewModel.deleteNote(userNoteId.toInt(), authViewModel.getUserId()?.toInt()!!)
                noteViewModel.deleteNote(
                    DeleteNoteRequest(
                        authViewModel.getDBGenerateId()!!,
                        noteDBId,
                        ntId,
                        authViewModel.getUserId()!!
                    )
                )
                bindDeleteObserver()
            }
        }

        restoreBtn?.setOnClickListener {
            balloon?.dismiss()
            lifecycleScope.launch {
                /*noteViewModel.restoreBinNote(
                    0,
                    authViewModel.getUserId()?.toInt()!!,
                    userNoteId.toInt()
                )*/
                noteViewModel.restoreNote(
                    SetAndRestoreRequest(
                        authViewModel.getDBGenerateId()!!,
                        noteDBId,
                        ntId,
                        authViewModel.getUserId()!!
                    )
                )
                bindRestoreObserver()
            }
        }

    }

    private fun bindDeleteObserver() {
        noteViewModel.noteDeleteLiveData.observe(viewLifecycleOwner) {
            binding.pBar.visibility = View.GONE
            when (it) {
                is NetworkResult.Success -> {
                    Toast.makeText(context, it.data!!.msg, Toast.LENGTH_SHORT).show()
                    getBinNotes()
                }

                is NetworkResult.Error -> {
                    showError(it.msg.toString())
                }

                is NetworkResult.Loading -> {
                    binding.pBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getBinNotes() {
        lifecycleScope.launch {
            noteViewModel.getBinNote(
                GetAllNotesRequest(
                    authViewModel.getDBGenerateId()!!,
                    authViewModel.getUserId()!!
                )
            )
            bindBinNoteObserver()
        }
    }

    private fun bindRestoreObserver() {
        noteViewModel.setAndRestore.observe(viewLifecycleOwner) {
            binding.pBar.visibility = View.GONE
            when (it) {
                is NetworkResult.Success -> {
                    getBinNotes()
                }

                is NetworkResult.Error -> {
                    showError(it.msg.toString())
                }

                is NetworkResult.Loading -> {
                    binding.pBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun bindBinNoteObserver() {
        noteViewModel.noteGetAllNotesLiveData.observe(viewLifecycleOwner) {
            binding.pBar.visibility = View.GONE
            when (it) {
                is NetworkResult.Success -> {
                    binAdapter = BinAdapter(
                        GetAllNotesResponse(it.data!!.data, it.data.msg, it.data.status),
                        this@BinFragment
                    )
                    val noteList = it.data.data
                    binding.notesRecycler.adapter = binAdapter
                    binding.notesRecycler.smoothScrollToPosition(binAdapter!!.itemCount)
                    if (noteList.isNotEmpty()) {
                        Log.d(ContentValues.TAG, noteList.toString())
                    } else {
                        Toast.makeText(context, "Empty notes", Toast.LENGTH_SHORT).show()
                    }
                }

                is NetworkResult.Error -> {
                    showError(it.msg)
                }

                is NetworkResult.Loading -> {
                    binding.pBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showError(msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    /*override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/

    /*override fun onItemClick(
        view: View,
        position: Int,
        noteId: Int,
        noteTitle: String,
        note: String,
        noteBackImage: Int
    ) {
        userNoteId = noteId.toString()
        balloon?.showAlignBottom(view)
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
        noteDBId = noteDatabaseId
        ntId = noteId
        balloon?.showAlignBottom(view)
    }

}