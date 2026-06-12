package com.example.plaintext.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Estado que representa as preferências do usuário e as entradas da tela de login.
 * Seguindo o padrão sênior, utilizamos imutabilidade (val e copy).
 */
data class PreferencesState(
    val login: String = "devtitans",
    val password: String = "123",
    val preencher: Boolean = true
)

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    handle: SavedStateHandle,
) : ViewModel() {

    // Estado centralizado e observável pelo Compose
    var preferencesState by mutableStateOf(PreferencesState())
        private set

    /**
     * Atualiza o login salvo nas preferências.
     */
    fun updateLogin(login: String) {
        preferencesState = preferencesState.copy(login = login)
    }

    /**
     * Atualiza a senha salva nas preferências.
     */
    fun updatePassword(password: String) {
        preferencesState = preferencesState.copy(password = password)
    }

    /**
     * Atualiza se os campos devem ser preenchidos automaticamente.
     */
    fun updatePreencher(preencher: Boolean) {
        preferencesState = preferencesState.copy(preencher = preencher)
    }

    /**
     * Valida as credenciais fornecidas contra os valores armazenados no estado.
     */
    fun checkCredentials(login: String, password: String): Boolean {
        return login == preferencesState.login && password == preferencesState.password
    }
}