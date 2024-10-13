package mx.com.u_life.presentation.screens.owner_screens.profile

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import mx.com.u_life.presentation.screens.owner_screens.profile.content.ProfileContent

@Composable
fun ProfileScreen(
    navController: NavController,
) {
    Scaffold(
        content = { innerPadding ->
            ProfileContent(
                paddingValues = innerPadding,
                navController = navController
            )

        }
    )
}