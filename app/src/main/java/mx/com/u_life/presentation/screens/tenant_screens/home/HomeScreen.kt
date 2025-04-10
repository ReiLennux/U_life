package mx.com.u_life.presentation.screens.tenant_screens.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mx.com.u_life.presentation.screens.tenant_screens.home.content.HomeContent
import mx.com.u_life.presentation.screens.tenant_screens.home.content.HomeView
import mx.com.u_life.presentation.screens.tenant_screens.home.content.HomeViewModel
import mx.com.u_life.presentation.screens.tenant_screens.home.content.RentDetailsView

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            var selectedIndex by remember { mutableIntStateOf(0) }
            val options = viewModel.types.observeAsState()

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    SingleChoiceSegmentedButtonRow {
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = 0,
                                count = (options.value?.size ?: 0) + 1
                            ),
                            onClick = {
                                selectedIndex = 0
                                viewModel.filterTypes(null)
                            },
                            selected = selectedIndex == 0,
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text(
                                text = "Todos",
                                fontSize = MaterialTheme.typography.labelSmall.fontSize
                            )
                        }

                        options.value?.forEachIndexed { index, item ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index + 1,
                                    count = (options.value?.size ?: 0) + 1
                                ),
                                onClick = {
                                    selectedIndex = index + 1
                                    viewModel.filterTypes(item)
                                },
                                selected = selectedIndex == index + 1,
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text(
                                    text = item.name,
                                    fontSize = MaterialTheme.typography.labelSmall.fontSize
                                )
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
