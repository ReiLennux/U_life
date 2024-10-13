package mx.com.u_life.presentation.screens.owner_screens.conversations.inbox

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import mx.com.u_life.presentation.screens.owner_screens.conversations.inbox.content.OwnerInboxContent

@Composable
fun OwnerInboxScreen(
    navController: NavController
) {
    Scaffold(
        content = { innerPadding ->
                OwnerInboxContent(paddingValues = innerPadding, navController = navController)

        }
    )
}