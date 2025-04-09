package mx.com.u_life.presentation.screens.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mx.com.u_life.presentation.screens.home.content.HomeContent
import mx.com.u_life.presentation.screens.home.content.HomeView
import mx.com.u_life.presentation.screens.home.content.HomeViewModel
import mx.com.u_life.presentation.screens.home.content.RentDetailsView

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            var selectedIndex by remember { mutableIntStateOf(0) }
            val options = viewModel.types

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                item{
                    SingleChoiceSegmentedButtonRow {
                        options.forEach { (key, value) ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(index = key, count = options.size),
                                onClick = {
                                    selectedIndex = key
                                    viewModel.filterTypes(value)
                                          },
                                selected = key == selectedIndex
                            ) {
                                Text(value)
                            }
                        }
                    }
                }

            }
        },
        content = { innerPadding ->
            HomeContent(paddingValues = innerPadding, navController = navController)
        }
    )

    HomeView(
        modifier = Modifier,
        navController = navController
    )

    RentDetailsView(
        modifier = Modifier,
        navController = navController
    )
}