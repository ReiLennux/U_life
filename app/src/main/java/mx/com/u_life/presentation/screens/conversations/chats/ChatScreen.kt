package mx.com.u_life.presentation.screens.conversations.chats

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import mx.com.u_life.R
import mx.com.u_life.presentation.components.DialogWithIcon
import mx.com.u_life.presentation.enums.Routes
import mx.com.u_life.presentation.screens.conversations.chats.content.ChatContent

@Composable
fun ChatScreen(
    navController: NavController,
) {
    Scaffold(
        content = { innerPadding ->
            ChatContent(paddingValues = innerPadding, navController = navController)
        }
    )
}