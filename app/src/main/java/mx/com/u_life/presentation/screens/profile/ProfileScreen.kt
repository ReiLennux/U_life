package mx.com.u_life.presentation.screens.profile

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import mx.com.u_life.presentation.screens.auth.login.content.LoginContent
import mx.com.u_life.presentation.screens.profile.content.ProfileContent

@Composable
fun ProfileScreen(
    navHostController: NavHostController
) {
    Scaffold(
        content = { innerPadding ->
            //ProfileContent(paddingValues = innerPadding, navHostController = navHostController)
            LoginContent(paddingValues = innerPadding, navHostController = navHostController)
        }
    )
}