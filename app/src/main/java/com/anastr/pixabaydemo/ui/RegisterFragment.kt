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
import com.anastr.pixabaydemo.databinding.FragmentRegisterBinding
import com.anastr.pixabaydemo.utils.onTextChanged
import com.anastr.pixabaydemo.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment: Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bRegister.setOnClickListener {
            viewModel.register(
                email = binding.inputEmail.editText!!.text.toString(),
                age = binding.inputAge.editText!!.text.toString().toIntOrNull() ?: 0,
                password = binding.inputPassword.editText!!.text.toString(),
            )
        }

        binding.inputEmail.editText?.onTextChanged { viewModel.clearEmailError() }
        binding.inputAge.editText?.onTextChanged { viewModel.clearAgeError() }
        binding.inputPassword.editText?.onTextChanged { viewModel.clearPasswordError() }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.emailErrorText.collect { errorMessage ->
                        binding.inputEmail.error = errorMessage
                    }
                }
                launch {
                    viewModel.ageErrorText.collect { errorMessage ->
                        binding.inputAge.error = errorMessage
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
                        binding.inputAge.isEnabled = !isLoading
                        binding.inputPassword.isEnabled = !isLoading
                        if (isLoading) {
                            binding.bRegister.visibility = View.GONE
                            binding.progress.visibility = View.VISIBLE
                        } else {
                            binding.bRegister.visibility = View.VISIBLE
                            binding.progress.visibility = View.GONE
                        }
                    }
                }
                launch {
                    viewModel.registerUserFlow.collect {
                        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
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