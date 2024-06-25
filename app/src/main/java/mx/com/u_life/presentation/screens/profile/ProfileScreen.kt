package mx.com.u_life.presentation.screens.profile

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import mx.com.u_life.presentation.screens.profile.content.ProfileContent

@Composable
fun ProfileScreen() {
    Scaffold(
        content = { innerPadding ->
            ProfileContent(paddingValues = innerPadding)
        }
    )
}