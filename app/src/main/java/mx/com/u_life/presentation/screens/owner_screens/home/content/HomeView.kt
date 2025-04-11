package mx.com.u_life.presentation.screens.owner_screens.home.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import mx.com.u_life.R
import mx.com.u_life.domain.models.Response
import mx.com.u_life.presentation.components.MessageDialogWithIcon

@Composable
fun HomeView(
    modifier: Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
){
    val response = viewModel.propertiesResponse.collectAsState()

    Box(modifier = modifier
        .fillMaxSize()
    ){
        response.let { stateFlow ->
            when (val resp = stateFlow.value) {
                is Response.Success -> {
                    LaunchedEffect(Unit) {
                        viewModel.assingPropertiesInfo(resp.data)
                    }
                }
                is Response.Loading -> {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .fillMaxWidth(),
                    )
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