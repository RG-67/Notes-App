package com.project.notesapp.ui.authentication

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.project.notesapp.R
import com.project.notesapp.databinding.RegistrationBinding
import com.project.notesapp.model.AuthModel
import com.project.notesapp.utils.Helper.Companion.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Registration : Fragment() {

    private var _binding: RegistrationBinding? = null
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
                lifecycleScope.launch {
                    val isExists = authViewModel.checkUserExists(binding.email.text.toString())
                    if (isExists == 0) {
                        authViewModel.register(getUserData)
                        findNavController().navigate(R.id.action_registration_to_login)
                    } else {
                        Snackbar.make(binding.root, "Email already exists", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                showError(validationResult.second)
            }
        }
        binding.signInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_registration_to_login)
        }

        binding.passwordToggle.setOnClickListener {
            if (binding.password.inputType == InputType.TYPE_CLASS_TEXT) {
                binding.password.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.passwordToggle.setImageResource(R.drawable.close_eye)
            } else {
                binding.password.inputType = InputType.TYPE_CLASS_TEXT
                binding.passwordToggle.setImageResource(R.drawable.open_eye)
            }
            binding.password.setSelection(binding.password.text.length)
        }

        binding.conPasswordToggle.setOnClickListener {
            if (binding.confirmPass.inputType == InputType.TYPE_CLASS_TEXT) {
                binding.confirmPass.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.conPasswordToggle.setImageResource(R.drawable.close_eye)
            } else {
                binding.confirmPass.inputType = InputType.TYPE_CLASS_TEXT
                binding.conPasswordToggle.setImageResource(R.drawable.open_eye)
            }
            binding.confirmPass.setSelection(binding.password.text.length)
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