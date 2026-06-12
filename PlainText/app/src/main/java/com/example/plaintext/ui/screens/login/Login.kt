package com.example.plaintext.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.plaintext.ui.screens.JetcasterAppState
import com.example.plaintext.ui.viewmodel.PreferencesState
import com.example.plaintext.ui.viewmodel.PreferencesViewModel

/**
 * Tela de Login - Composable principal
 * @param appState - Objeto que gerencia a navegação entre telas do app
 * @param viewModel - ViewModel que contém a lógica de negócio e estado da tela
 *                   hiltViewModel() injeta automaticamente o ViewModel usando Hilt
 */
@Composable
fun Login_screen(
    appState: JetcasterAppState,
    viewModel: PreferencesViewModel = hiltViewModel()
) {
    LoginContent(
        state = viewModel.preferencesState,
        onUpdatePreencher = { viewModel.updatePreencher(it) },
        checkCredentials = { u, p -> viewModel.checkCredentials(u, p) },
        navigateToSettings = { appState.navigateToPreferences() },
        navigateToList = { appState.navigateToList() }
    )
}


/**
 * Conteúdo da tela de Login - Função privada que implementa o layout
 * @param state - Estado atual das preferências (login, senha, preencher)
 * @param onUpdatePreencher - Função lambda chamada quando o checkbox é alterado
 * @param checkCredentials - Função lambda que valida usuário e senha
 * @param navigateToSettings - Função lambda para navegar para tela de configurações
 * @param navigateToList - Função lambda para navegar para tela de lista
 */
@Composable
private fun LoginContent(
    state: PreferencesState,
    onUpdatePreencher: (Boolean) -> Unit,
    checkCredentials: (String, String) -> Boolean,
    navigateToSettings: () -> Unit,
    navigateToList: () -> Unit
) {
    val context = LocalContext.current

    // Estados locais para os campos de entrada
    // rememberSaveable mantém o estado mesmo durante rotação da tela
    // mutableStateOf cria um estado observável que atualiza a UI quando muda
    var username by rememberSaveable { mutableStateOf(if (state.preencher) state.login else "") }
    var password by rememberSaveable { mutableStateOf(if (state.preencher) state.password else "") }

    // Scaffold é o layout principal que fornece estrutura básica da tela
    // topBar - barra superior com menu
    // paddingValues - espaçamento interno automático do sistema
    Scaffold(
        topBar = {
            TopBarComponent(
                navigateToSettings = navigateToSettings,
                navigateToSensores = { /* Opcional */ }
            )
        }
    ) { paddingValues ->
        // Column organiza os elementos verticalmente
        // Modifier - usado para modificar o comportamento/aparência do componente
        // fillMaxSize() - ocupa toda a tela disponível
        // padding() - adiciona espaçamento
        // verticalScroll() - permite rolagem quando conteúdo excede a tela
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Bem-vindo",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // Spacer adiciona espaço vazio entre elementos
            Spacer(modifier = Modifier.height(32.dp))

            // OutlinedTextField - campo de texto com borda
            // value - valor atual do campo
            // onValueChange - função chamada quando o texto muda (it = novo texto)
            // label - texto de rótulo do campo
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Usuário") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // PasswordVisualTransformation() - esconde os caracteres da senha
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Row organiza os elementos horizontalmente
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = state.preencher,
                    onCheckedChange = { onUpdatePreencher(it) }
                )
                Text(text = "Lembrar credenciais", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // onClick - função executada quando o botão é pressionado
            Button(
                onClick = {
                    if (username.length > 0 && password.length > 0) {
                        if (checkCredentials(username, password)) {
                            navigateToList()
                        } else {
                            Toast.makeText(context, "Login/Senha invalidos", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar")
            }
        }
    }
}

/**
 * Preview da tela de Login - Permite visualizar a tela no Android Studio sem executar no emulador
 * showBackground = true - mostra o fundo da tela
 * mockAppState - cria um objeto falso de appState para o preview funcionar
 */
@Preview(showBackground = true)
@Composable
fun Login_screenPreview() {
    val mockAppState = remember {
        object : JetcasterAppState(
            navController = androidx.navigation.compose.rememberNavController(),
            context = androidx.compose.ui.platform.LocalContext.current
        ) {}
    }
    Login_screen(
        appState = mockAppState,
        viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    )
}

@Composable
fun MyAlertDialog(shouldShowDialog: MutableState<Boolean>) {
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = { shouldShowDialog.value = false },
            title = { Text(text = "Sobre") },
            text = { Text(text = "PlainText Password Manager v1.0") },
            confirmButton = {
                Button(onClick = { shouldShowDialog.value = false }) {
                    Text(text = "Ok")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    navigateToSettings: (() -> Unit?)? = null,
    navigateToSensores: (() -> Unit?)? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    val shouldShowDialog = remember { mutableStateOf(false) }

    MyAlertDialog(shouldShowDialog = shouldShowDialog)

    TopAppBar(
        title = { Text("PlainText") },
        actions = {
            if (navigateToSettings != null && navigateToSensores != null) {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Configurações") },
                        onClick = {
                            navigateToSettings()
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Sobre") },
                        onClick = {
                            shouldShowDialog.value = true
                            expanded = false
                        }
                    )
                }
            }
        }
    )
}