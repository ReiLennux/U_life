package mx.com.u_life.presentation.screens.auth.signUp.content

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mx.com.u_life.R
import mx.com.u_life.domain.models.Response
import mx.com.u_life.presentation.enums.Routes

@Composable
fun SignUpView(
    modifier: Modifier,
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val login by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        login?.let { isRegister ->
            when (isRegister) {
                is Response.Loading -> {

                CircularProgressIndicator()

                }

                is Response.Error -> {

                    viewModel.resetInitState()

                }

                is Response.Success -> {
                    Toast.makeText(
                        context,
                        stringResource(id = R.string.auth_success_register),
                        Toast.LENGTH_LONG
                    ).show()
                    navController.navigate(Routes.HOME.name) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = false
                        }
                    }
                    viewModel.resetInitState()
                }

                else -> {}
            }
        }
    }
}