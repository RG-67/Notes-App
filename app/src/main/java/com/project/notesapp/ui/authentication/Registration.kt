package com.project.notesapp.ui.authentication

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.project.notesapp.R
import com.project.notesapp.databinding.RegistrationBinding
import com.project.notesapp.model.AuthModel
import com.project.notesapp.model.userRequestModel.UserRegisterRequest
import com.project.notesapp.utils.Helper.Companion.hideKeyboard
import com.project.notesapp.utils.NetworkResult
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
//                val getUserData = getUserCred()
                val getUserData = getCredForUser()
                Log.d("UserReq ==>", getUserData.toString())
                lifecycleScope.launch {
                    authViewModel.registerUser(getUserData)
                    bindObserver()
                    /*val isExists = authViewModel.checkUserExists(binding.email.text.toString())
                    if (isExists == 0) {
                        authViewModel.register(getUserData)
                        findNavController().navigate(R.id.action_registration_to_login)
                    } else {
                        Snackbar.make(binding.root, "Email already exists", Snackbar.LENGTH_SHORT)
                            .show()
                    }*/
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

    private fun getCredForUser(): UserRegisterRequest {
        return binding.run {
            UserRegisterRequest(
                email.text.toString(),
                name.text.toString(),
                password.text.toString(),
                phone.text.toString().toLong()
            )
        }
    }

    private fun validation(): Pair<Boolean, String> {
        val name = binding.name.text.toString()
        val phone = binding.phone.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val conPassword = binding.confirmPass.text.toString()
        return authViewModel.validateRegister(name, phone, email, password, conPassword, false)
    }

    private fun showError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT).show()
    }

    private fun bindObserver() {
        try {
            authViewModel.userResponseLiveData.observe(viewLifecycleOwner) {
                binding.pBar.visibility = View.GONE
                when (it) {
                    is NetworkResult.Success -> {
                        Log.d("Message1 ==>", it.toString())
                        findNavController().navigate(R.id.action_registration_to_login)
                    }

                    is NetworkResult.Error -> {
                        Log.d("Message2 ==>", it.msg.toString())
                        showUserCreateError(it.msg.toString())
                    }

                    is NetworkResult.Loading -> {
                        Log.d("Message3 ==>", it.toString())
                        binding.pBar.visibility = View.VISIBLE
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("ExceptionMsg ==>", "${e.message}")
            e.printStackTrace()
        }
    }

    private fun showUserCreateError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}