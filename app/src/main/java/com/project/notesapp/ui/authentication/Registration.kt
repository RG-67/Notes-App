package com.project.notesapp.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.project.notesapp.R
import com.project.notesapp.databinding.RegistrationBinding
import com.project.notesapp.model.AuthModel
import com.project.notesapp.utils.Helper.Companion.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Registration : Fragment() {

    private var _binding: RegistrationBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerBtn.setOnClickListener {
            hideKeyboard(it)
            val validationResult = validation()
            if (validationResult.first) {
                val getUserData = getUserCred()
                if (authViewModel.register(getUserData))
            } else {
                showError(validationResult.second)
            }
        }
    }

    private fun getUserCred(): AuthModel {
        return binding.run {
            AuthModel(
                0,
                name.text.toString(),
                email.text.toString(),
                password.text.toString()
            )
        }
    }

    private fun validation(): Pair<Boolean, String> {
        val name = binding.name.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val conPassword = binding.confirmPass.text.toString()
        return authViewModel.validateRegister(name, email, password, conPassword, false)
    }

    private fun showError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}