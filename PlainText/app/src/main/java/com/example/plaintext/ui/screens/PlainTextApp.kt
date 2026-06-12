package com.example.plaintext.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.ui.screens.editList.EditList
import com.example.plaintext.ui.screens.hello.Hello_screen
import com.example.plaintext.ui.screens.list.AddButton
import com.example.plaintext.ui.screens.list.ListView
import com.example.plaintext.ui.screens.login.Login_screen
import com.example.plaintext.ui.screens.login.TopBarComponent
import com.example.plaintext.ui.screens.preferences.SettingsScreen
import com.example.plaintext.ui.viewmodel.ListViewModel
import com.example.plaintext.ui.viewmodel.PreferencesViewModel
import com.example.plaintext.utils.parcelableType
import kotlin.reflect.typeOf
import com.example.plaintext.ui.screens.preferences.SettingsScreen
/**
 * Função principal do app que define a navegação entre telas
 * @param appState - Gerenciador de navegação, criado automaticamente se não for fornecido
 *                   rememberJetcasterAppState() mantém o estado durante recomposições
 */
@Composable
fun PlainTextApp(
    appState: JetcasterAppState = rememberJetcasterAppState()
) {
    // NavHost - container que gerencia as telas e a navegação
    // navController - controlador de navegação do appState
    // startDestination - tela inicial do app (Screen.Login)
    NavHost(
        navController = appState.navController,
        startDestination = Screen.Login,
    )
    {
        // composable - define cada tela da navegação
        // Screen.Hello - tela de boas-vindas
        // toRoute - converte a rota atual em objeto tipado
        composable<Screen.Hello>{
            var args = it.toRoute<Screen.Hello>()
            Hello_screen(args)
        }
        // Screen.Login - tela de login
        // appState - passado para permitir navegação para outras telas
        composable<Screen.Login>{
            Login_screen(
                appState = appState
            )
        }
        // Screen.EditList - tela de edição de senha
        // typeMap - mapeamento necessário para passar objetos complexos na navegação
        composable<Screen.EditList>(
            typeMap = mapOf(typeOf<PasswordInfo>() to parcelableType<PasswordInfo>())
        ) {
            val args = it.toRoute<Screen.EditList>()
            EditList(
                args,
                navigateBack = {},
                savePassword = { password -> Unit }
            )
        }
        // Screen.Preferences - tela de configurações
        composable<Screen.Preferences> {
            SettingsScreen(navController = appState.navController)
        }
        // Screen.List - tela de lista de senhas
        // hiltViewModel() - injeta o ViewModel automaticamente
        // ListView - componente que exibe a lista

        composable<Screen.List> {
            val listViewModel: ListViewModel = hiltViewModel()
            ListView(
                viewModel = listViewModel,
                navigateToAdd = {
                    appState.navController.navigate(Screen.EditList(PasswordInfo(0, "", "", "")))
                },
                navigateToEdit = { password ->
                    appState.navController.navigate(Screen.EditList(password))
                },
                navigateToSettings = {
                    appState.navigateToPreferences()
                }
            )
        }
    }
}
