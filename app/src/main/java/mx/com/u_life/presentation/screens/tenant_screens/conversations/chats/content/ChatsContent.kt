package mx.com.u_life.presentation.screens.tenant_screens.conversations.chats.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mx.com.u_life.presentation.components.GenericCard
import mx.com.u_life.R
import mx.com.u_life.domain.models.chats.ChatModel
import mx.com.u_life.presentation.components.ChatOverview
import mx.com.u_life.presentation.components.GenericSimpleButton
import mx.com.u_life.presentation.enums.Routes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun InboxContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: InboxViewModel = hiltViewModel()
) {
    val userChats by viewModel.userChats.observeAsState(emptyList())

    if (userChats.isEmpty()) {
        EmptyInboxMessage(navController)
    } else {
        InboxMessages(navController = navController, chats = userChats)
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
fun InboxMessages(navController: NavController, chats: List<ChatModel>) {
    LazyColumn {
        items(items = chats) { chat ->
            ChatOverview(
                image = R.drawable.fumo,
                name = chat.userName,
                message = chat.lastMessage,
                time = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(chat.timestamp)),
                onClickMessage = {
                    navController.navigate("${Routes.CHAT.name}/${chat.userId}")
                }
            )
        }
    }
}