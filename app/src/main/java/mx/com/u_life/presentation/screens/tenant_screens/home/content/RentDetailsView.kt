package mx.com.u_life.presentation.screens.tenant_screens.home.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mx.com.u_life.domain.models.Response

@Composable
fun RentDetailsView(
    modifier: Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val rents by viewModel.rentDetailResponse.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        rents?.let { response ->
            when (response) {
                is Response.Loading -> {}

                is Response.Error -> {

                    viewModel.resetRentDetails()

                }

                is Response.Success -> {

                    viewModel.assignRentDetails(response.data)

                }

                else -> {}
            }
        }
    }
}