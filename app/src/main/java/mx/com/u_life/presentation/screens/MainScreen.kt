package mx.com.u_life.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import mx.com.u_life.presentation.enums.Routes
import mx.com.u_life.presentation.navigation.BottomNavBar
import mx.com.u_life.presentation.navigation.TopAppBar
import mx.com.u_life.presentation.screens.chats.ChatsScreen
import mx.com.u_life.presentation.screens.home.HomeScreen
import mx.com.u_life.presentation.screens.profile.ProfileScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MainScreen() {
    AppContent()
}

@Composable
fun AppContent() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopAppBar() },
        bottomBar = { BottomNavBar( navController ) },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Routes.HOME.name){
                HomeScreen()
            }
            composable(route = Routes.CHATS.name){
                ChatsScreen()
            }
            composable(route = Routes.PROFILE.name){
                ProfileScreen()
            }
        }
    }
}