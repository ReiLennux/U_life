package mx.com.u_life.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mx.com.u_life.presentation.enums.Routes
import mx.com.u_life.presentation.navigation.BottomNavBar
import mx.com.u_life.presentation.navigation.TopAppBar
import androidx.navigation.compose.NavHost
import mx.com.u_life.presentation.enums.OwnerRoutes
import mx.com.u_life.presentation.navigation.owner.OwnerBottomNavBar
import mx.com.u_life.presentation.navigation.owner.OwnerTopAppBar
import mx.com.u_life.presentation.navigation.owner.ownerRoutes
import mx.com.u_life.presentation.navigation.studentRoutes

@Composable
fun MainScreen() {
    val viewModel: MainScreenViewModel = hiltViewModel()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentUser by viewModel.currentUser.observeAsState(initial = null)
    MainScreenView(modifier = Modifier)
    when (currentUser?.userType) {
        "owner" -> {
            Scaffold(
                topBar = {
                    if (currentRoute != Routes.CHAT.name) {
                        OwnerTopAppBar(visible = viewModel.verifyRouteTop(currentRoute = currentRoute))
                    }
                },
                bottomBar = {
                    if (currentRoute != Routes.CHAT.name) {
                        OwnerBottomNavBar(navController, visible = viewModel.verifyRouteBottom(currentRoute = currentRoute))
                    }
                }
            ) { paddingValues ->
                NavHost(
                    modifier = Modifier.padding(paddingValues),
                    navController = navController,
                    startDestination = OwnerRoutes.OWNER_HOME.name
                ) {
                    ownerRoutes(navController = navController)
                }
            }
        }
        "student" -> {
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
        else -> {
        }
    }
}


