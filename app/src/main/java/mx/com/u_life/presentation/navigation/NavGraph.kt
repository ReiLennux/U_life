package mx.com.u_life.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.com.u_life.presentation.enums.Routes
import mx.com.u_life.presentation.screens.auth.login.LoginScreen
import mx.com.u_life.presentation.screens.auth.signUp.SignUpScreen
import mx.com.u_life.presentation.screens.tenant_screens.config.verification.VerificationScreen
import mx.com.u_life.presentation.screens.tenant_screens.conversations.inbox.InboxScreen
import mx.com.u_life.presentation.screens.tenant_screens.conversations.chats.ChatScreen
import mx.com.u_life.presentation.screens.tenant_screens.home.HomeScreen
import mx.com.u_life.presentation.screens.tenant_screens.profile.ProfileScreen

fun NavGraphBuilder.studentRoutes(navController: NavController, isLoggedIn: Boolean) {
    composable(Routes.HOME.name) { HomeScreen(navController) }
    composable(Routes.INBOX.name) { InboxScreen(navController) }
    composable(Routes.CHAT.name) { ChatScreen(navController, isLoggedIn) }
    composable(Routes.PROFILE.name) { ProfileScreen(navController, isLoggedIn) }
    composable(Routes.LOGIN.name) { LoginScreen(navController) }
    composable(Routes.SIGN_UP.name) { SignUpScreen(navController) }
    composable(Routes.VERIFY_PROFILE.name) { VerificationScreen(navController) }
}

fun NavGraphBuilder.landlordRoutes(navController: NavController) {
    composable(Routes.LOGIN.name) { LoginScreen(navController) }
    composable(Routes.SIGN_UP.name) { SignUpScreen(navController) }
}