package mx.com.u_life.presentation.screens.auth.login

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import mx.com.u_life.presentation.screens.auth.login.content.LoginContent

@Composable
fun LoginScreen(
    navHostController: NavHostController
) {
    Scaffold(
        content = { innerPadding ->
            LoginContent(paddingValues = innerPadding, navHostController = navHostController)
        }
    )
}