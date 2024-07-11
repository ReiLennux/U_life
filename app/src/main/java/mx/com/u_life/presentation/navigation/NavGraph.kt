package mx.com.u_life.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.com.u_life.presentation.enums.Routes
import mx.com.u_life.presentation.screens.auth.login.LoginScreen
import mx.com.u_life.presentation.screens.auth.signUp.SignUpScreen
import mx.com.u_life.presentation.screens.conversations.inbox.InboxScreen
import mx.com.u_life.presentation.screens.home.HomeScreen
import mx.com.u_life.presentation.screens.profile.ProfileScreen

fun NavGraphBuilder.studentRoutes(navController: NavController, isLoggedIn: Boolean) {
    composable(Routes.HOME.name) { HomeScreen(navController) }
    composable(Routes.CHATS.name) { InboxScreen(navController, isLoggedIn) }
    composable(Routes.PROFILE.name) { ProfileScreen(navController, isLoggedIn) }
    composable(Routes.LOGIN.name) { LoginScreen(navController) }
    composable(Routes.SIGN_UP.name) { SignUpScreen(navController) }
}

fun NavGraphBuilder.landlordRoutes(navController: NavController) {
    composable(Routes.LOGIN.name) { LoginScreen(navController) }
    composable(Routes.SIGN_UP.name) { SignUpScreen(navController) }
}