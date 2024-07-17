package mx.com.u_life.presentation.screens.conversations.chats

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import mx.com.u_life.presentation.screens.conversations.chats.content.ChatContent

@Composable
fun ChatScreen(
    navController: NavController,
) {
    Scaffold(
        topBar = {

        },
        content = { innerPadding ->
            ChatContent(paddingValues = innerPadding, navController = navController)
        },
        bottomBar = {

        }
    )
}