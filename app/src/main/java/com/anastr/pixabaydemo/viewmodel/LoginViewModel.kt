package com.anastr.pixabaydemo.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anastr.pixabaydemo.domain.entity.User
import com.anastr.pixabaydemo.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
): ViewModel() {

    private val _emailErrorText = MutableStateFlow("")
    val emailErrorText = _emailErrorText.asStateFlow()

    private val _passwordErrorText = MutableStateFlow("")
    val passwordErrorText = _passwordErrorText.asStateFlow()

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private val loginUserChannel = Channel<User>()
    val loginUserFlow = loginUserChannel.receiveAsFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val isValid = validate(email, password)
            if (isValid) {
                _loadingState.update { true }
                try {
                    val user = loginRepository.login(email, password)
                    loginUserChannel.send(user)
                } catch (e: Exception) {
                    // TODO Handle errors.
                }
                _loadingState.update { false }
            }
        }
    }

    fun clearPasswordError() { _passwordErrorText.update { "" } }

    fun clearEmailError() { _emailErrorText.update { "" } }

    private fun validate(email: String, password: String): Boolean {
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.length in 6..12

        // TODO Get string from res.
        if (!isEmailValid) {
            _emailErrorText.update { "Email is not valid!" }
        }
        if (!isPasswordValid) {
            _passwordErrorText.update { "Password must be between 6 to 12!" }
        }
        return isEmailValid && isPasswordValid
    }
}
