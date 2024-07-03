package mx.com.u_life.presentation.screens.auth.signUp.content

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import mx.com.u_life.R
import mx.com.u_life.presentation.components.AppLogo
import mx.com.u_life.presentation.components.GenericOutlinedButton
import mx.com.u_life.presentation.components.GenericTextField
import mx.com.u_life.presentation.components.PasswordTextField

@Composable
fun SignUpContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
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
            SignUpForm(navController, viewModel)
        }
    }
}

@Composable
fun SignUpForm(navController: NavController, viewModel: SignUpViewModel) {
    val name by viewModel.name.observeAsState("")
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val confirmPassword by viewModel.confirmPassword.observeAsState("")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(10.dp)
    ) {
        AppLogo()
        HorizontalDivider()
        Spacer(modifier = Modifier.height(20.dp))
        //Text field for name
        GenericTextField(
            value = name,
            onValueChange = {
                viewModel.onChanged(
                    name = it,
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword
                )
            },
            leadingIcon = R.drawable.ic_person,
            placeholder = R.string.auth_register_name,
            action = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(10.dp))
        //Text field for email
        GenericTextField(
            value = email,
            onValueChange = {
                viewModel.onChanged(
                    name = name,
                    email = it,
                    password = password,
                    confirmPassword = confirmPassword
                )
            },
            leadingIcon = R.drawable.ic_email,
            placeholder = R.string.auth_login_email,
            action = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(10.dp))
        //Text field for password
        PasswordTextField(
            value = password,
            onTextFieldChange = {
                viewModel.onChanged(
                    name = name,
                    email = email,
                    password = it,
                    confirmPassword = confirmPassword
                )
            },
            painterResource = R.drawable.ic_pass,
            placeholder = R.string.auth_login_password,
            action = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(10.dp))
        //Text field for confirm password
        PasswordTextField(
            value = password,
            onTextFieldChange = {
                viewModel.onChanged(
                    name = name,
                    email = email,
                    password = confirmPassword,
                    confirmPassword = it
                )
            },
            painterResource = R.drawable.ic_pass,
            placeholder = R.string.auth_register_confirm_password,
            action = ImeAction.Done
        )
        Spacer(modifier = Modifier.height(15.dp))
        //Button for SignUp
        GenericOutlinedButton(
            text = R.string.auth_register_title,
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            onClick = { /*TODO*/ }
        )
    }

}
