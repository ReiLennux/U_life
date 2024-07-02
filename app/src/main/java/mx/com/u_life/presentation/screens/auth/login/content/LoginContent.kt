package mx.com.u_life.presentation.screens.auth.login.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import mx.com.u_life.R
import mx.com.u_life.presentation.components.AppLogo
import mx.com.u_life.presentation.components.GenericOutlinedButton
import mx.com.u_life.presentation.components.GenericTextField
import mx.com.u_life.presentation.components.PasswordTextField

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navHostController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
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
            LoginForm(navHostController, viewModel)
        }
    }
}

@Composable
fun LoginForm(navHostController: NavHostController, viewModel: LoginViewModel) {
    val email by viewModel.email.observeAsState("")
    val emailError by viewModel.emailError.observeAsState(false)
    val password by viewModel.password.observeAsState("")
    val passwordError by viewModel.passwordError.observeAsState(false)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(10.dp)
    ) {
        AppLogo()
        HorizontalDivider()
        Spacer(modifier = Modifier.height(20.dp))
        GenericTextField(
            value = email,
            onValueChange = {
                viewModel.onChanged(
                    email = it,
                    password = password
                )
            },
            leadingIcon = R.drawable.ic_email,
            placeholder = R.string.auth_login_email,
            action = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(10.dp))

        PasswordTextField(
            value = password,
            onTextFieldChange = {
                viewModel.onChanged(
                    email = email,
                    password = it
                )
            },
            painterResource = R.drawable.ic_password,
            placeholder = R.string.auth_login_password,
            action = ImeAction.Done
        )
        Spacer(modifier = Modifier.height(15.dp))
        GenericOutlinedButton(
            text = R.string.auth_login_title,
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            onClick = { /*TODO*/ }
        )
    }

}
