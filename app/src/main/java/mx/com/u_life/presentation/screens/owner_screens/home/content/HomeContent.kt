package mx.com.u_life.presentation.screens.owner_screens.home.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mx.com.u_life.presentation.components.GenericPropertiesCard

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(1.dp),
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val img = "https://img10.naventcdn.com/avisos/resize/18/00/59/13/82/95/1200x1200/1484323586.jpg"
    val mutableList = remember {
        List(3) { img }
    }


    Box(
        modifier = modifier.padding(paddingValues = paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GenericPropertiesCard(
                title = "El nombre de la propiedad",
                address = "Direccion de la propiedad #124, col sin nombre, tula de allende Hgo",
                items = mutableList,
                price = "2500 / mes",
                state = "Disponible"
            )
        }
    }
}
