package com.project.notesapp.ui.authentication

import android.content.ContentValues.TAG
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.project.notesapp.R
import com.project.notesapp.databinding.LoginBinding
import com.project.notesapp.model.AuthModel
import com.project.notesapp.utils.Helper.Companion.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        if (authViewModel.getUserId() != "") {
            findNavController().navigate(R.id.action_login_to_note)
        }

        binding.signBtn.setOnClickListener {
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        binding.loginBtn.setOnClickListener {
//            findNavController().navigate(R.id.note)
            /*            authViewModel.getUser.observe(requireActivity()) { response ->
                            val userList = response as ArrayList<AuthModel>
                            if (userList.size > 0) {
                                Log.d("UserListData ==>", userList.toString())
                            } else {
                                Snackbar.make(binding.root, userList.size.toString(), Snackbar.LENGTH_SHORT)
                                    .show()
                            }
                        }*/
            hideKeyboard(it)
            val validationResult = validation()
            if (validationResult.first) {
                lifecycleScope.launch {
                    val user = authViewModel.getUserLogin(
                        binding.email.text.toString(),
                        binding.password.text.toString()
                    )
                    if (user.isNotEmpty()) {
                        authViewModel.setUserId(user[0].id.toString())
                        authViewModel.setUserName(user[0].name)
                        authViewModel.setUserEmail(user[0].userName)
                        findNavController().navigate(R.id.action_login_to_note)
                    } else {
                        Snackbar.make(binding.root, "Invalid credentials", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                showError(validationResult.second)
            }
        }
        binding.signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_registration)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validation(): Pair<Boolean, String> {
        val userName = binding.email.text.toString()
        val password = binding.password.text.toString()
        return authViewModel.validateRegister("", userName, password, "", true)
    }

    private fun showError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT).show()
    }

}