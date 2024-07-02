package mx.com.u_life.presentation.screens.auth.signUp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import mx.com.u_life.presentation.screens.auth.login.content.LoginContent
import mx.com.u_life.presentation.screens.auth.login.content.LoginView
import mx.com.u_life.presentation.screens.auth.signUp.content.SignUpContent
import mx.com.u_life.presentation.screens.auth.signUp.content.SignUpView

@Composable
fun SignUpScreen(
    navHostController: NavHostController
) {
    Scaffold(
        content = { innerPadding ->
            SignUpContent(paddingValues = innerPadding, navHostController = navHostController)
        }
    )
    SignUpView(modifier = Modifier.fillMaxSize())
}