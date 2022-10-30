package com.anastr.pixabaydemo.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anastr.pixabaydemo.domain.entity.User
import com.anastr.pixabaydemo.domain.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository,
): ViewModel() {

    private val _emailErrorText = MutableStateFlow("")
    val emailErrorText = _emailErrorText.asStateFlow()

    private val _ageErrorText = MutableStateFlow("")
    val ageErrorText = _ageErrorText.asStateFlow()

    private val _passwordErrorText = MutableStateFlow("")
    val passwordErrorText = _passwordErrorText.asStateFlow()

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private val registerUserChannel = Channel<User>()
    val registerUserFlow = registerUserChannel.receiveAsFlow()

    fun register(email: String, age: Int, password: String) {
        viewModelScope.launch {
            val isValid = validate(email, age, password)
            if (isValid) {
                _loadingState.update { true }
                try {
                    val user = registerRepository.register(email, age, password)
                    registerUserChannel.send(user)
                } catch (e: Exception) {
                    // TODO Handle errors.
                }
                _loadingState.update { false }
            }
        }
    }

    fun clearEmailError() { _emailErrorText.update { "" } }

    fun clearAgeError() { _ageErrorText.update { "" } }

    fun clearPasswordError() { _passwordErrorText.update { "" } }

    private fun validate(email: String, age: Int, password: String): Boolean {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isAgeValid = age in 18..99
        val isPasswordValid = password.length in 6..12

        // TODO Get string from res.
        if (!isEmailValid) {
            _emailErrorText.update { "Email is not valid!" }
        }
        if (!isAgeValid) {
            _ageErrorText.update { "Age must be between 18 to 99!" }
        }
        if (!isPasswordValid) {
            _passwordErrorText.update { "Password must be between 6 to 12!" }
        }
        return isEmailValid && isPasswordValid
    }
}
