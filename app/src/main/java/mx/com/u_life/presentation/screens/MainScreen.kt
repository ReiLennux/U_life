package mx.com.u_life.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mx.com.u_life.presentation.enums.Routes
import mx.com.u_life.presentation.navigation.BottomNavBar
import mx.com.u_life.presentation.navigation.TopAppBar
import androidx.navigation.compose.NavHost
import mx.com.u_life.presentation.navigation.studentRoutes

@Composable
fun MainScreen() {
    val viewModel: MainScreenViewModel = hiltViewModel()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isLoggedIn = viewModel.isLoggedIn.collectAsState()

    Scaffold(
        topBar = {
            if (currentRoute != Routes.CHAT.name) {
                TopAppBar(visible = viewModel.verifyRouteTop(currentRoute = currentRoute))
            }
        },
        bottomBar = {
            if (currentRoute != Routes.CHAT.name) {
                BottomNavBar(navController, visible = viewModel.verifyRouteBottom(currentRoute = currentRoute))
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = Routes.HOME.name
        ) {
            studentRoutes(navController = navController, isLoggedIn = isLoggedIn.value)
        }
    }
}

