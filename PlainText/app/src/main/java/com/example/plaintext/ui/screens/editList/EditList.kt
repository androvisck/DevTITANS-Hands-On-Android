package com.example.plaintext.ui.screens.editList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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

    val nomeState = rememberSaveable {
        mutableStateOf(args.password.name)
    }

    val usuarioState = rememberSaveable {
        mutableStateOf(args.password.login)
    }

    val senhaState = rememberSaveable {
        mutableStateOf(args.password.password)
    }

    val notasState = rememberSaveable {
        mutableStateOf(args.password.notes ?: "")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        EditInput(
            textInputLabel = "Nome",
            textInputState = nomeState
        )

        EditInput(
            textInputLabel = "Usuário",
            textInputState = usuarioState
        )

        EditInput(
            textInputLabel = "Senha",
            textInputState = senhaState
        )

        EditInput(
            textInputLabel = "Notas",
            textInputState = notasState
        )

        Button(
            onClick = {

                savePassword(
                    PasswordInfo(
                        id = args.password.id,
                        name = nomeState.value,
                        login = usuarioState.value,
                        password = senhaState.value,
                        notes = notasState.value
                    )
                )

                navigateBack()
            }
        ) {
            Text("Salvar")
        }
    }
}

@Composable
fun EditInput(
    textInputLabel: String,
    textInputState: MutableState<String> = mutableStateOf(""),
    textInputHeight: Int = 60
) {
    val padding: Int = 30
    var textState by rememberSaveable { textInputState }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(textInputHeight.dp)
            .padding(horizontal = padding.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Text(textInputLabel) },
            modifier = Modifier
                .height(textInputHeight.dp)
                .fillMaxWidth()
        )
    } Spacer (modifier = Modifier.height(10.dp))
}