package com.project.notesapp.ui.authentication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.project.notesapp.R
import com.project.notesapp.databinding.LoginBinding
import com.project.notesapp.model.AuthModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Login : Fragment() {

    private var _binding: LoginBinding? = null
    private val binding get() = _binding!!
    private var context: Context? = null

    private val authViewModel by activityViewModels<AuthViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signBtn.setOnClickListener {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        binding.loginBtn.setOnClickListener {
//            findNavController().navigate(R.id.note)
            /*authViewModel.getUser.observe(requireActivity()) { response ->
                val userList = response as ArrayList<AuthModel>
                if (userList.size > 0) {
                    Log.d("UserListData ==>", userList.toString())
                } else {
                    Snackbar.make(binding.root, userList.size.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }
            }*/
        }
        binding.signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.registration)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}