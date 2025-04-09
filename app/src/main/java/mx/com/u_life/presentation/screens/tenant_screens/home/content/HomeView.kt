package mx.com.u_life.presentation.screens.tenant_screens.home.content

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
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
import mx.com.u_life.presentation.screens.auth.login.content.LoginViewModel

@Composable
fun HomeView(
    modifier: Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val login by viewModel.rentsResponse.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        login?.let { result ->
            when (result) {
                is Response.Loading -> {

                    CircularProgressIndicator()

                }

                is Response.Error -> {

                    viewModel.resetRents()

                }

                is Response.Success -> {

                    viewModel.assignRentsResult(result.data)

                }

                else -> {}
            }
        }
    }
}