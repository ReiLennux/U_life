package mx.com.u_life.presentation.screens.owner_screens.home.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mx.com.u_life.domain.models.rents.RentModel
import mx.com.u_life.presentation.components.GenericPropertiesCard

@Composable
fun HomeContent(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    listState: LazyListState
) {
    val rents = viewModel.properties.collectAsState().value
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(rents) { rent ->
                PropertiesCard(rent = rent)
            }
        }
    }
}

@Composable
fun PropertiesCard(rent: RentModel) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GenericPropertiesCard(
            title = rent.name,
            address = rent.location?.address.orEmpty(),
            items = rent.images,
            price = rent.price.toString(),
            state = rent.state
        )
    }
}
