package mx.com.u_life.presentation.screens.owner_screens.addproperty.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mx.com.u_life.domain.models.Response
import mx.com.u_life.R
import mx.com.u_life.presentation.components.MessageDialogWithIcon

@Composable
fun AddPropertyPostView(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AddPropertyViewModel = hiltViewModel()
) {
    val addPropertyState = viewModel.isLoading.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        addPropertyState.let { stateFlow ->
            when (val response = stateFlow.value) {
                is Response.Loading -> {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .fillMaxWidth(),
                    )
                }
                is Response.Success -> {
                    if (response.data) {

                        MessageDialogWithIcon(
                            onDismissRequest = {
                                viewModel.resetInitialState()
                                navController.popBackStack()
                            },
                            onConfirmation = {
                                viewModel.resetInitialState()
                                navController.popBackStack()
                                             },
                            dialogTitle = R.string.property_success_title,
                            dialogText = R.string.property_success_body,
                            icon = Icons.Filled.CheckCircleOutline
                        )
                    }
                }
                is Response.Error -> {
                    MessageDialogWithIcon(
                        onDismissRequest = { viewModel.resetInitialState() },
                        onConfirmation = { viewModel.resetInitialState() },
                        dialogTitle = R.string.property_error_title,
                        dialogText = R.string.property_error_body,
                        icon = Icons.Filled.Clear
                    )
                }
                else -> {}
            }
        }
    }
}
