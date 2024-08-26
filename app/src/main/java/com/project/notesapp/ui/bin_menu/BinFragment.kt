package com.project.notesapp.ui.bin_menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.notesapp.R
import com.project.notesapp.databinding.FragmentBinBinding

class BinFragment : Fragment() {

    private var _binding: FragmentBinBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBinBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}