package com.example.plaintext.ui.screens.editList

import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.ui.screens.Screen
import com.example.plaintext.ui.screens.login.TopBarComponent

@Composable
fun EditList(
    args: Screen.EditList,
    navigateBack: () -> Unit,
    savePassword: (password: PasswordInfo) -> Unit
) {
    var nome by rememberSaveable { mutableStateOf(args.password.name) }
    var usuario by rememberSaveable { mutableStateOf(args.password.login) }
    var senha by rememberSaveable { mutableStateOf(args.password.password) }
    var notas by rememberSaveable { mutableStateOf(args.password.notes) }

    Scaffold(
        topBar = {
            TopBarComponent()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color(0xFF8BC34A)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (args.password.id == 0) "Adicionar nova senha" else "Editar senha",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            EditInput(textInputLabel = "Nome", textInputState = nome, onValueChange = { nome = it })
            EditInput(textInputLabel = "Usuário", textInputState = usuario, onValueChange = { usuario = it })
            EditInput(textInputLabel = "Senha", textInputState = senha, onValueChange = { senha = it })
            EditInput(textInputLabel = "Notas", textInputState = notas, onValueChange = { notas = it }, textInputHeight = 120)

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        savePassword(
                            PasswordInfo(
                                id = args.password.id,
                                name = nome,
                                login = usuario,
                                password = senha,
                                notes = notas
                            )
                        )
                        navigateBack()
                    },
                    modifier = Modifier.height(50.dp)
                ) {
                    Text("Salvar")
                }
            }
        }
    }
}


@Composable
fun EditInput(
    textInputLabel: String,
    textInputState: String,
    onValueChange: (String) -> Unit,
    textInputHeight: Int = 60
) {
    val padding: Int = 30

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(textInputHeight.dp)
            .padding(horizontal = padding.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            value = textInputState,
            onValueChange = onValueChange,
            label = { Text(textInputLabel) },
            modifier = Modifier
                .height(textInputHeight.dp)
                .fillMaxWidth()
        )

    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview(showBackground = true)
@Composable
fun EditListPreview() {
    EditList(
        Screen.EditList(PasswordInfo(1, "Nome", "Usuário", "Senha", "Notas")),
        navigateBack = {},
        savePassword = {}
    )
}

@Preview(showBackground = true)
@Composable
fun EditInputPreview() {
    EditInput("Nome", "", {})
}
