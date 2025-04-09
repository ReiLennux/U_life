package mx.com.u_life.presentation.screens.owner_screens.conversations.chats

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import mx.com.u_life.R
import mx.com.u_life.presentation.components.ChatName
import mx.com.u_life.presentation.screens.tenant_screens.conversations.chats.content.ChatContent

@Composable
fun OwnerChatScreen(
    navController: NavController,
) {
    Scaffold(
        topBar = {
            ChatName(
                image = R.drawable.fumo,
                name = "Peluche Chistoso",
                onClickBack = {
                    navController.popBackStack()
                }
            )
        },
        content = { innerPadding ->
            ChatContent(paddingValues = innerPadding, navController = navController)
        },
        bottomBar = {

        }
    )
}