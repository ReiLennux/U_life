package mx.com.u_life.presentation.screens.home

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import mx.com.u_life.presentation.screens.home.content.HomeContent

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    Scaffold(
        content = { innerPadding ->
            HomeContent(paddingValues = innerPadding)
        }
    )
}