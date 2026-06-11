package com.example.plaintext.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class LoginViewState(
    val login: String = "",
    val password: String = "",
    val saveInfo: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(LoginViewState())
        private set

    fun onLoginChanged(login: String) {
        state = state.copy(login = login)
    }

    fun onPasswordChanged(password: String) {
        state = state.copy(password = password)
    }

    fun onSaveInfoChanged(saveInfo: Boolean) {
        state = state.copy(saveInfo = saveInfo)
    }
}
