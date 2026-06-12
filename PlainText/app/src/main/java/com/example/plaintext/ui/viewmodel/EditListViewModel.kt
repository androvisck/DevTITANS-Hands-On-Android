package com.example.plaintext.ui.viewmodel
import androidx.compose.foundation.content.MediaType.Companion.PlainText
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plaintext.data.model.toInfo
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.data.repository.PasswordDBStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditListViewModel @Inject constructor(
    private val repository: PasswordDBStore
) : ViewModel() {

    var passwordInfo by mutableStateOf(
        PasswordInfo(
            name = "",
            login = "",
            password = "",
            notes = ""
        )
    )
        private set

    fun loadPassword(id: Int) {
        viewModelScope.launch {
            repository.get(id)?.let { password ->
                passwordInfo = password.toInfo()
            }
        }
    }

    fun updateName(name: String) {
        passwordInfo = passwordInfo.copy(name = name)
    }

    fun updateLogin(login: String) {
        passwordInfo = passwordInfo.copy(login = login)
    }

    fun updatePassword(password: String) {
        passwordInfo = passwordInfo.copy(password = password)
    }

    fun updateNotes(notes: String) {
        passwordInfo = passwordInfo.copy(notes = notes)
    }

    fun save() {
        viewModelScope.launch {
            repository.save(passwordInfo)
        }
    }
}