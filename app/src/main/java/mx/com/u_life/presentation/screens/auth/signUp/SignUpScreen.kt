package mx.com.u_life.presentation.screens.auth.signUp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import mx.com.u_life.presentation.screens.auth.signUp.content.SignUpContent
import mx.com.u_life.presentation.screens.auth.signUp.content.SignUpView

@Composable
fun SignUpScreen(
    navController: NavController
) {
    Scaffold(
        content = { innerPadding ->
            SignUpContent(paddingValues = innerPadding, navController = navController)
        }
    )
    SignUpView(modifier = Modifier.fillMaxSize())
}