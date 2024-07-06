package mx.com.u_life.presentation.screens.auth.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import mx.com.u_life.presentation.screens.auth.login.content.LoginContent
import mx.com.u_life.presentation.screens.auth.login.content.LoginView

@Composable
fun LoginScreen(
    navController: NavController
) {
    Scaffold(
        content = { innerPadding ->
            LoginContent(paddingValues = innerPadding, navController = navController)
        }
    )
    LoginView(modifier = Modifier.fillMaxSize(), navController = navController)
}