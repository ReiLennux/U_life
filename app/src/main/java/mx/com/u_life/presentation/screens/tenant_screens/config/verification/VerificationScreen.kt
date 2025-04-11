package mx.com.u_life.presentation.screens.tenant_screens.config.verification

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mx.com.u_life.presentation.screens.tenant_screens.config.verification.content.VerificationContent
import mx.com.u_life.presentation.screens.tenant_screens.config.verification.content.VerificationViewModel


@Composable
fun VerificationScreen(
    navController: NavController,
    viewModel: VerificationViewModel = hiltViewModel()
){
    Scaffold(
        topBar = {},
        content = {
            VerificationContent(
                paddingValues = it,
                navController = navController,
                viewModel = viewModel
            )
        }
    )
}