package mx.com.u_life.presentation.screens.auth.signUp.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import mx.com.u_life.R
import mx.com.u_life.presentation.components.AppLogo
import mx.com.u_life.presentation.components.GenericOutlinedButton
import mx.com.u_life.presentation.components.GenericTextField
import mx.com.u_life.presentation.components.PasswordTextField
import mx.com.u_life.presentation.enums.Routes

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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                AppLogo()
                HorizontalDivider()
                Spacer(modifier = Modifier.height(20.dp))
                SignUpForm(navController, viewModel)
            }
        }
    }
}

@Composable
fun SignUpForm(navController: NavController, viewModel: SignUpViewModel) {
    val scope = rememberCoroutineScope()
    //Variables
    val name by viewModel.name.observeAsState("")
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val confirmPassword by viewModel.confirmPassword.observeAsState("")
    //Error variables
    val nameError by viewModel.nameError.observeAsState("")
    val emailError by viewModel.emailError.observeAsState("")
    val passwordError by viewModel.passwordError.observeAsState("")
    val repeatedPasswordError by viewModel.repeatedPasswordError.observeAsState("")

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(10.dp)
    ) {
        //Text field for name
        GenericTextField(
            value = name,
            onValueChange = {
                viewModel.onEvent(
                    SignUpFormEvent.NameChanged(it)
                )
            },
            leadingIcon = R.drawable.ic_person,
            placeholder = R.string.auth_register_name,
            action = ImeAction.Next,
            errorMessage = nameError
        )
        Spacer(modifier = Modifier.height(10.dp))
        //Text field for email
        GenericTextField(
            value = email,
            onValueChange = {
                viewModel.onEvent(
                    SignUpFormEvent.EmailChanged(it)
                )
            },
            leadingIcon = R.drawable.ic_email,
            placeholder = R.string.auth_login_email,
            action = ImeAction.Next,
            errorMessage = emailError
        )
        Spacer(modifier = Modifier.height(10.dp))
        //Text field for password
        PasswordTextField(
            value = password,
            onTextFieldChange = {
                viewModel.onEvent(
                    SignUpFormEvent.PasswordChanged(it)
                )
            },
            painterResource = R.drawable.ic_pass,
            placeholder = R.string.auth_login_password,
            action = ImeAction.Next,
            errorMessage = passwordError
        )
        Spacer(modifier = Modifier.height(10.dp))
        //Text field for confirm password
        PasswordTextField(
            value = confirmPassword,
            onTextFieldChange = {
                viewModel.onEvent(
                    SignUpFormEvent.RepeatedPasswordChanged(it)
                )
            },
            painterResource = R.drawable.ic_pass,
            placeholder = R.string.auth_register_confirm_password,
            action = ImeAction.Done,
            errorMessage = repeatedPasswordError
        )
        Spacer(modifier = Modifier.height(15.dp))
        //Button for SignUp
        GenericOutlinedButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = R.string.auth_register_title,
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            onClick = {
                scope.launch {
                    viewModel.onEvent(
                        SignUpFormEvent.Submit
                    )
                }
            }
        )
    }

}
