package com.example.plaintext.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.data.model.toInfo
import com.example.plaintext.data.repository.PasswordDBStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// CORREÇÃO: Definido emptyList() como padrão e alterado para 'val' para manter o estado imutável
data class ListViewState(
    val passwordList: List<PasswordInfo> = emptyList(),
    val isCollected: Boolean = false
)

@HiltViewModel
open class ListViewModel @Inject constructor(
    private val passwordDBStore: PasswordDBStore
) : ViewModel() {

    // Agora o ListViewState() inicializa sem erros usando a lista vazia padrão
    var listViewState by mutableStateOf(ListViewState())
        private set

    init {
        viewModelScope.launch {
            passwordDBStore.getList().collect { passwords ->
                val infoList = passwords.map { it.toInfo() }

                listViewState = listViewState.copy(
                    passwordList = infoList,
                    isCollected = true
                )
            }
        }
    }
    

    fun savePassword(password: PasswordInfo) {
        viewModelScope.launch {
            passwordDBStore.save(password)
        }
    }
}
