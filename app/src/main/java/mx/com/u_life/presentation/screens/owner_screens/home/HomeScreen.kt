package mx.com.u_life.presentation.screens.owner_screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import mx.com.u_life.presentation.enums.OwnerRoutes
import mx.com.u_life.presentation.screens.owner_screens.home.content.HomeContent
import mx.com.u_life.presentation.screens.owner_screens.home.content.HomeView

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OwnerHomeScreen(
    navController: NavController
) {
    val listState = rememberLazyListState()
    val expandedFab by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    Scaffold(
        content = { _ ->
            HomeContent( navController = navController, listState = listState)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate(OwnerRoutes.OWNER_ADD_PROPERTY.name) },
                expanded = expandedFab,
                icon = { Icon(Icons.Filled.Add, "Add property") },
                text = { Text(text = "Añadir propiedad") },
                contentColor = contentColorFor(MaterialTheme.colorScheme.onPrimary)
            )
        }
    )
    HomeView(modifier = Modifier.fillMaxSize())
}