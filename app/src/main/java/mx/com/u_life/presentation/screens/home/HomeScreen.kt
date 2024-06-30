package mx.com.u_life.presentation.screens.home

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import mx.com.u_life.presentation.screens.home.content.HomeContent

@Composable
fun HomeScreen(
    navHostController: NavHostController
) {
    Scaffold(
        content = { innerPadding ->
            HomeContent(paddingValues = innerPadding, navHostController = navHostController)
        }
    )
}