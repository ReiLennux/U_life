package mx.com.u_life.presentation.screens.auth.login.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import mx.com.u_life.R
import mx.com.u_life.presentation.components.AppLogo
import mx.com.u_life.presentation.components.GenericOutlinedButton
import mx.com.u_life.presentation.components.GenericTextField
import mx.com.u_life.presentation.components.PasswordTextField
import mx.com.u_life.presentation.enums.Routes

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavController,
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
            LoginForm(navController, viewModel)
        }
    }
}

@Composable
fun LoginForm(navController: NavController, viewModel: LoginViewModel) {
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
        //Text Field for email
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
        //Text Field for password
        PasswordTextField(
            value = password,
            onTextFieldChange = {
                viewModel.onChanged(
                    email = email,
                    password = it
                )
            },
            painterResource = R.drawable.ic_pass,
            placeholder = R.string.auth_login_password,
            action = ImeAction.Done
        )
        Spacer(modifier = Modifier.height(15.dp))
        //Button for login
        GenericOutlinedButton(
            text = R.string.auth_login_title,
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            onClick = { /*TODO*/ }
        )
        Spacer(modifier = Modifier.height(15.dp))
        //Text Button for SignUp
        Row(
            modifier = Modifier.padding(10.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.auth_no_account),
                color = MaterialTheme.colorScheme.inversePrimary,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                modifier = Modifier.clickable{ navController.navigate(Routes.SIGN_UP.name) },
                text = stringResource(id = R.string.auth_register_title),
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        }
    }

}