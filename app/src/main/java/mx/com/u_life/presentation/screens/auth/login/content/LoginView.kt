package mx.com.u_life.presentation.screens.auth.login.content

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import mx.com.u_life.domain.models.Response
import mx.com.u_life.R

@Composable
fun LoginView(
    modifier: Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val login by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    Box(modifier = modifier) {
        login?.let { islogin ->
            when (islogin) {
                is Response.Loading -> {



                }

                is Response.Error -> {
                    viewModel.resetInitState()
                    Toast.makeText(
                        context,
                        stringResource(id = R.string.auth_error_login),
                        Toast.LENGTH_LONG
                    ).show()
                }

                is Response.Success -> {
                    LaunchedEffect(Unit) {

                    }
                }

                else -> {}
            }
        }
    }
}