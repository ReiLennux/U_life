package mx.com.u_life.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import mx.com.u_life.domain.models.Response

@Composable
fun MainScreenView(
    modifier: Modifier,
    viewModel: MainScreenViewModel = hiltViewModel()
){
    val currentUser = viewModel.userResponse.collectAsState()

    Box(modifier = modifier){
        currentUser.let{ stateFlow ->
            when (val response = stateFlow.value) {
                is Response.Success -> {
                    LaunchedEffect(Unit) {
                        viewModel.assignCurrentUser(response.data)
                    }
                }
                is Response.Error -> {
                    viewModel.resetInitialState()
                }
                Response.Loading ->{
                    println("cargando...")
                }
                else -> {}
            }
        }
    }
}