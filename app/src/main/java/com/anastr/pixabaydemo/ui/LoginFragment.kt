package com.anastr.pixabaydemo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.anastr.pixabaydemo.R
import com.anastr.pixabaydemo.databinding.FragmentLoginBinding
import com.anastr.pixabaydemo.utils.onTextChanged
import com.anastr.pixabaydemo.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment: Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bLogin.setOnClickListener {
            viewModel.login(
                email = binding.inputEmail.editText!!.text.toString(),
                password = binding.inputPassword.editText!!.text.toString(),
            )
        }
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.inputEmail.editText?.onTextChanged { viewModel.clearEmailError() }
        binding.inputPassword.editText?.onTextChanged { viewModel.clearPasswordError() }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.emailErrorText.collect { errorMessage ->
                        binding.inputEmail.error = errorMessage
                    }
                }
                launch {
                    viewModel.passwordErrorText.collect { errorMessage ->
                        binding.inputPassword.error = errorMessage
                    }
                }
                launch {
                    viewModel.loadingState.collect { isLoading ->
                        binding.inputEmail.isEnabled = !isLoading
                        binding.inputPassword.isEnabled = !isLoading
                        binding.tvRegister.isEnabled = !isLoading
                        if (isLoading) {
                            binding.bLogin.visibility = View.GONE
                            binding.progress.visibility = View.VISIBLE
                        } else {
                            binding.bLogin.visibility = View.VISIBLE
                            binding.progress.visibility = View.GONE
                        }
                    }
                }
                launch {
                    viewModel.loginUserFlow.collect {
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}