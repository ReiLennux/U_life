package mx.com.u_life.presentation.screens.owner_screens.addproperty.content

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mx.com.u_life.domain.models.Response

@Composable
fun AddPropertyView(
    modifier: Modifier,
    viewModel: AddPropertyViewModel = hiltViewModel(),
) {
    val types = viewModel.typesResponse.collectAsState()

    Box(modifier = modifier) {
        types.let{ stateFlow ->
            when (val response = stateFlow.value) {
                is Response.Success -> {
                    LaunchedEffect(Unit) {
                        viewModel.assignTypes(response.data)
                    }
                }
                is Response.Error -> {
                    viewModel.resetInitialState()
                }
                Response.Loading ->{
                }
                else -> {}
            }

        }
    }
}