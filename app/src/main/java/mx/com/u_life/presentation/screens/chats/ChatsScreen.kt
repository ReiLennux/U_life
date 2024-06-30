package mx.com.u_life.presentation.screens.chats

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import mx.com.u_life.presentation.screens.chats.content.ChatsContent

@Composable
fun ChatsScreen(
    navHostController: NavHostController
) {
    Scaffold(
        content = { innerPadding ->
            ChatsContent(paddingValues = innerPadding, navHostController = navHostController)
        }
    )
}