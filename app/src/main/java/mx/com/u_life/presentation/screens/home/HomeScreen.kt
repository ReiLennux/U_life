package mx.com.u_life.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavController
import mx.com.u_life.presentation.screens.home.content.HomeContent
import mx.com.u_life.presentation.screens.home.content.HomeView
import mx.com.u_life.presentation.screens.home.content.RentDetailsView

@Composable
fun HomeScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            var selectedIndex by remember { mutableIntStateOf(0) }
            val options = listOf("Cuartos", "Dptos.", "Casas")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ){
                SingleChoiceSegmentedButtonRow {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                            onClick = { selectedIndex = index },
                            selected = index == selectedIndex
                        ) {
                            Text(label)
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