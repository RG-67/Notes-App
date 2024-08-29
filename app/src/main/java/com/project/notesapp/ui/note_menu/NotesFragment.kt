package com.project.notesapp.ui.note_menu

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.TypefaceSpan
import android.text.style.UnderlineSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.project.notesapp.R
import com.project.notesapp.databinding.FragmentNotesBinding
import com.project.notesapp.databinding.PaletteLayoutBinding
import com.project.notesapp.model.NoteModel
import com.project.notesapp.ui.authentication.AuthViewModel
import com.project.notesapp.utils.Helper
import com.project.notesapp.utils.ItemClickListener
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.balloon
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesFragment : Fragment(), ItemClickListener {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel by activityViewModels<NoteViewModel>()
    private var context: Context? = null
    private val authViewModel by activityViewModels<AuthViewModel>()
    private var noteAdapter: NoteAdapter? = null
    private var noteItemPosition = 0
    private var userNoteId = ""
    private var title = ""
    private var noteContent = ""
    private var flag = 0

    private var balloon: Balloon? = null
    private var editBtn: LinearLayout? = null
    private var deleteBtn: LinearLayout? = null
    private var noteOptionLayout: View? = null

    private var paletteBalloon: Balloon? = null
    private var paletteOptionLayout: View? = null
    private var paletteRadioGr: RadioGroup? = null
    private var noneRdBtn: RadioButton? = null
    private var back1: RadioButton? = null
    private var back2: RadioButton? = null
    private var back3: RadioButton? = null
    private var back4: RadioButton? = null
    private var back5: RadioButton? = null

    private var fontBalloon: Balloon? = null
    private var fontOptionLayout: View? = null
    private var fontRadioGr: RadioGroup? = null
    private var sansDef: RadioButton? = null
    private var sansBlack: RadioButton? = null
    private var cardoReg: RadioButton? = null
    private var cardoBold: RadioButton? = null
    private var cardoItalic: RadioButton? = null
    private var fanwoodReg: RadioButton? = null
    private var fanwoodItalic: RadioButton? = null
    private var honkReg: RadioButton? = null
    private var notoColorReg: RadioButton? = null
    private var poppinsReg: RadioButton? = null
    private var poppinsMedium: RadioButton? = null
    private var poppinsSemibold: RadioButton? = null
    private var poppinsItalic: RadioButton? = null
    private var robotoReg: RadioButton? = null
    private var robotoMedium: RadioButton? = null
    private var robotoItalic: RadioButton? = null
    private var titilliReg: RadioButton? = null
    private var titilliSemibold: RadioButton? = null
    private var titilliBold: RadioButton? = null
    private var titilliItalic: RadioButton? = null

    private var selectedFaceType: Typeface? = null
    private var underlineEnable: Boolean = false

    private var underlineFlag = 1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)

        noteOptionLayout = layoutInflater.inflate(R.layout.note_option_layout, null)
        paletteOptionLayout = layoutInflater.inflate(R.layout.palette_layout, null)
        fontOptionLayout = layoutInflater.inflate(R.layout.font_selection_layout, null)

        binding.fabBtn.setImageResource(R.drawable.add)
        binding.fabBtn.imageTintList =
            ColorStateList.valueOf(Color.WHITE)

        balloon = Balloon.Builder(requireContext())
            .setLayout(noteOptionLayout!!)
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
        editBtn = balloon!!.getContentView().findViewById(R.id.editBtn)
        deleteBtn = balloon!!.getContentView().findViewById(R.id.deleteBtn)

        paletteBalloon = Balloon.Builder(requireContext())
            .setLayout(paletteOptionLayout!!)
            .setIsVisibleArrow(false)
            .setWidthRatio(0.8f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setBalloonAnimation(BalloonAnimation.FADE)
            .build()
        paletteRadioGr = paletteBalloon!!.getContentView().findViewById(R.id.paletteRadioGr)
        noneRdBtn = paletteBalloon!!.getContentView().findViewById(R.id.noneRdBtn)
        back1 = paletteBalloon!!.getContentView().findViewById(R.id.back1)
        back2 = paletteBalloon!!.getContentView().findViewById(R.id.back2)
        back3 = paletteBalloon!!.getContentView().findViewById(R.id.back3)
        back4 = paletteBalloon!!.getContentView().findViewById(R.id.back4)
        back5 = paletteBalloon!!.getContentView().findViewById(R.id.back5)

        fontBalloon = Balloon.Builder(requireContext())
            .setLayout(fontOptionLayout!!)
            .setIsVisibleArrow(false)
            .setWidthRatio(0.8f)
            .setWidth(BalloonSizeSpec.WRAP)
            .setHeight(400)
            .setCornerRadius(5f)
            .setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .build()
        fontRadioGr = fontBalloon!!.getContentView().findViewById(R.id.fontRadioBtn)
        sansDef = fontBalloon!!.getContentView().findViewById(R.id.sansDef)
        sansBlack = fontBalloon!!.getContentView().findViewById(R.id.sansBlack)
        cardoReg = fontBalloon!!.getContentView().findViewById(R.id.cardoReg)
        cardoBold = fontBalloon!!.getContentView().findViewById(R.id.cardoBold)
        cardoItalic = fontBalloon!!.getContentView().findViewById(R.id.cardoItalic)
        fanwoodReg = fontBalloon!!.getContentView().findViewById(R.id.fanwoodReg)
        fanwoodItalic = fontBalloon!!.getContentView().findViewById(R.id.fanwoodItalic)
        honkReg = fontBalloon!!.getContentView().findViewById(R.id.honkReg)
        notoColorReg = fontBalloon!!.getContentView().findViewById(R.id.notoColorReg)
        poppinsReg = fontBalloon!!.getContentView().findViewById(R.id.poppinsReg)
        poppinsMedium = fontBalloon!!.getContentView().findViewById(R.id.poppinsMedium)
        poppinsSemibold = fontBalloon!!.getContentView().findViewById(R.id.poppinsSemibold)
        poppinsItalic = fontBalloon!!.getContentView().findViewById(R.id.poppinsItalic)
        robotoReg = fontBalloon!!.getContentView().findViewById(R.id.robotoReg)
        robotoMedium = fontBalloon!!.getContentView().findViewById(R.id.robotoMedium)
        robotoItalic = fontBalloon!!.getContentView().findViewById(R.id.robotoItalic)
        titilliReg = fontBalloon!!.getContentView().findViewById(R.id.titilliReg)
        titilliSemibold = fontBalloon!!.getContentView().findViewById(R.id.titilliSemibold)
        titilliBold = fontBalloon!!.getContentView().findViewById(R.id.titilliBold)
        titilliItalic = fontBalloon!!.getContentView().findViewById(R.id.titilliItalic)

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
                    noteAdapter = NoteAdapter(it, this@NotesFragment)
                    val noteList = it as ArrayList<NoteModel>
                    binding.notesRecycler.adapter = noteAdapter
                    binding.notesRecycler.smoothScrollToPosition(noteAdapter!!.itemCount)
                    if (noteList.size > 0) {
                        Log.d(TAG, noteList.toString())
                    } else {
                        Toast.makeText(context, "Empty notes", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        editBtn?.setOnClickListener {
            balloon?.dismiss()
            flag = 2
            showHide()
            binding.title.setText(title)
            binding.note.setText(noteContent)
        }

        deleteBtn?.setOnClickListener {
            balloon?.dismiss()
            lifecycleScope.launch {
//                noteViewModel.deleteNote(userNoteId.toInt(), authViewModel.getUserId()?.toInt()!!)
                noteViewModel.updateIsDelete(
                    1,
                    authViewModel.getUserId()?.toInt()!!,
                    userNoteId.toInt()
                )
            }
        }

        binding.fabBtn.setOnClickListener {
            flag = 1
            showHide()
        }
        binding.cancelBtn.setOnClickListener {
            showHide()
        }
        binding.saveBtn.setOnClickListener {
            val getValidation = getValidation()
            if (binding.saveBtn.text == "Save") {
                val getNoteData = getNoteData()
                if (getValidation.second) {
                    lifecycleScope.launch {
                        Log.d(TAG, getNoteData.toString())
                        noteViewModel.insertNoteData(getNoteData)
                        showHide()
                        Helper.hideKeyboard(binding.root)
                    }
                } else {
                    showError(getValidation.first)
                }
            } else {
                if (getValidation.second) {
                    lifecycleScope.launch {
                        noteViewModel.updateNotes(
                            Helper.getDate(),
                            Helper.getCurrentTime(),
                            binding.title.text.toString(),
                            binding.note.text.toString(),
                            authViewModel.getUserId()!!.toInt(),
                            userNoteId.toInt()
                        )
                        showHide()
                        Helper.hideKeyboard(binding.root)
                    }
                } else {
                    showError(getValidation.first)
                }
            }
        }

        binding.note.addTextChangedListener(textWatcher)

        binding.underline.setOnClickListener {
            if (underlineFlag == 1) {
                underlineFlag = 2
                underlineEnable = true
                binding.underline.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorOnPrimary
                    )
                )
                binding.underline.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    ), android.graphics.PorterDuff.Mode.MULTIPLY
                )
            } else {
                underlineFlag = 1
                underlineEnable = false
                binding.underline.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.transparent_green
                    )
                )
                binding.underline.setColorFilter(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.icon_color
                    ), android.graphics.PorterDuff.Mode.MULTIPLY
                )
            }
        }

        binding.palette.setOnClickListener {
            showPopUp("palette", it)
        }

        noneRdBtn?.setOnClickListener {
            setPaletteRadio(R.id.noneRdBtn)
        }
        back1?.setOnClickListener {
            setPaletteRadio(R.id.back1)
        }
        back2?.setOnClickListener {
            setPaletteRadio(R.id.back2)
        }
        back3?.setOnClickListener {
            setPaletteRadio(R.id.back3)
        }
        back4?.setOnClickListener {
            setPaletteRadio(R.id.back4)
        }
        back5?.setOnClickListener {
            setPaletteRadio(R.id.back5)
        }

        binding.font.setOnClickListener {
            showPopUp("font", it)
        }
        fontRadioGr?.setOnCheckedChangeListener { _, p1 ->
            selectedFaceType = noteViewModel.getTypeface(p1, requireContext())
            fontBalloon?.dismiss()
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
                Helper.getCurrentTime(),
                0
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
            if (flag == 1) {
                val saveText = "Save"
                binding.saveBtn.text = saveText
                binding.saveBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.save, 0, 0, 0)
                binding.title.setText("")
                binding.note.setText("")
            } else {
                val updateText = "Update"
                binding.saveBtn.text = updateText
                binding.saveBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.update, 0, 0, 0)
            }
        } else {
            binding.addNoteRel.visibility = View.GONE
            binding.noteListRel.visibility = View.VISIBLE
        }
    }

    /*override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/

    override fun onItemClick(
        view: View,
        position: Int,
        noteId: Int,
        noteTitle: String,
        note: String
    ) {
        noteItemPosition = position
        userNoteId = noteId.toString()
        title = noteTitle
        noteContent = note
        showPopUp("note", view)
    }

    private fun showPopUp(from: String, view: View) {
        when (from) {
            "note" -> {
                balloon?.showAlignBottom(view)
            }

            "palette" -> {
                paletteBalloon?.showAlignBottom(view)
            }

            "font" -> {
                fontBalloon?.showAlignBottom(view)
            }

            else -> Toast.makeText(context, "Invalid selection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setPaletteRadio(radioBtn: Int) {
        val drawableList = noteViewModel.setPalette("palette", radioBtn)
        noneRdBtn?.isChecked = drawableList[0]
        back1?.isChecked = drawableList[1]
        back2?.isChecked = drawableList[2]
        back3?.isChecked = drawableList[3]
        back4?.isChecked = drawableList[4]
        back5?.isChecked = drawableList[5]
        paletteBalloon?.dismiss()
    }

    private val textWatcher = object : TextWatcher {
        private var startPos = 0
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            startPos = p1
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        @RequiresApi(Build.VERSION_CODES.P)
        override fun afterTextChanged(p0: Editable?) {
            p0?.let {
                val endPos = startPos + it.length - startPos
                val spannableString = SpannableStringBuilder(it)
                if (selectedFaceType != null && endPos <= it.length) {
                    spannableString.setSpan(
                        TypefaceSpan(selectedFaceType!!),
                        startPos, endPos,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                if (underlineEnable && endPos <= it.length) {
                    spannableString.setSpan(
                        UnderlineSpan(),
                        startPos,
                        endPos,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                binding.note.removeTextChangedListener(this)
                binding.note.text = spannableString
                binding.note.setSelection(endPos)
                binding.note.addTextChangedListener(this)
            }
        }

    }


}