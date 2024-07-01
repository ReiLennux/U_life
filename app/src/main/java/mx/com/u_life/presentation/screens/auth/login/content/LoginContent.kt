package mx.com.u_life.presentation.screens.auth.login.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import mx.com.u_life.R
import mx.com.u_life.presentation.components.GenericCard
import mx.com.u_life.presentation.components.GenericTextField
import mx.com.u_life.presentation.screens.chats.content.RedirectToSearch

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navHostController: NavHostController,
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
            LoginForm()
        }
    }
}

@Composable
fun LoginForm() {
    /*
    GenericTextField(
        value = ,
        onValueChange = ,
        placeholder = R.string.auth_login_email,
    )

     */
}
