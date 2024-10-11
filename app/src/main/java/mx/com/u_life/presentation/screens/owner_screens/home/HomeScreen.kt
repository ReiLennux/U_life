package mx.com.u_life.presentation.screens.owner_screens.home

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import mx.com.u_life.presentation.screens.owner_screens.home.content.HomeContent

@Composable
fun HomeScreen(
    navController: NavController
) {
    Scaffold(
        content = { innerPadding ->
            HomeContent(paddingValues = innerPadding, navController = navController)
        }
    )
}