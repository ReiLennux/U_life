package mx.com.u_life.presentation.screens.tenant_screens.profile

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import mx.com.u_life.R
import mx.com.u_life.presentation.components.DialogWithIcon
import mx.com.u_life.presentation.enums.Routes
import mx.com.u_life.presentation.screens.tenant_screens.profile.content.ProfileContent

@Composable
fun ProfileScreen(
    navController: NavController,
    isLoggedIn: Boolean
) {
    Scaffold(
        content = { innerPadding ->
            if (isLoggedIn){
                ProfileContent(
                    paddingValues = innerPadding,
                    navController = navController
                )
            } else {
                DialogWithIcon(
                    onDismissRequest = {
                        navController.popBackStack()
                    },
                    onConfirmation = {
                        navController.navigate(Routes.LOGIN.name) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = false
                            }
                        }
                    },
                    dialogTitle = R.string.auth_dialog_tittle,
                    dialogText = R.string.auth_dialog_body,
                    icon = R.drawable.ic_login
                )
            }
        }
    )
}