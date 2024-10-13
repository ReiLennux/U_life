package mx.com.u_life.presentation.screens.owner_screens.conversations.inbox.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mx.com.u_life.presentation.components.GenericCard
import mx.com.u_life.R
import mx.com.u_life.presentation.components.ChatOverview
import mx.com.u_life.presentation.components.GenericSimpleButton
import mx.com.u_life.presentation.enums.OwnerRoutes
import mx.com.u_life.presentation.enums.Routes

@Composable
fun OwnerInboxContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavController
) {
    Box{
        //EmptyInboxMessage(navController)
        InboxMessages(navController)
    }
}

@Composable
fun RedirectToSearch(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        GenericSimpleButton(
            text = R.string.chats_card_Search,
            onClick = { navController.navigate(Routes.HOME.name) }
        )
    }
}

@Composable
fun EmptyInboxMessage(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GenericCard(
            title = R.string.chats_card_tittle,
            description = R.string.chats_card_description,
            content = { RedirectToSearch(navController) },
            icon = R.drawable.img_person_searching
        )
    }
}

@Composable
fun InboxMessages(navController: NavController){
    ChatOverview(
        image = R.drawable.fumo,
        name = "Peluche Chistoso",
        message = "Hola como estas",
        onClickMessage = {
            navController.navigate(OwnerRoutes.OWNER_CHAT.name)
        }
    )
}