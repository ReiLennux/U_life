package mx.com.u_life.presentation.screens.chats.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.com.u_life.presentation.components.GenericCard
import mx.com.u_life.R
import mx.com.u_life.presentation.components.GenericOutlinedButton

@Composable
fun ChatsContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
) {
    Box(
        modifier = modifier.padding(paddingValues = paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GenericCard(
                title = R.string.chats_card_tittle,
                description = R.string.chats_card_description,
                content = { RedirectToSearch() },
            )
        }
    }
}

@Composable
fun RedirectToSearch() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.fumo), contentDescription = null)
        Spacer(modifier = Modifier.height(16.dp))
        GenericOutlinedButton(
            text = R.string.chats_card_Search,
            icon = R.drawable.baseline_search,
            onClick = { /*TODO*/ }
        )
    }
}