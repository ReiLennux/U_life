package mx.com.u_life.presentation.navigation.owner

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.com.u_life.presentation.enums.OwnerRoutes
import mx.com.u_life.presentation.screens.owner_screens.addproperty.AddPropertyScreen
import mx.com.u_life.presentation.screens.owner_screens.conversations.chats.OwnerChatScreen
import mx.com.u_life.presentation.screens.owner_screens.conversations.inbox.OwnerInboxScreen
import mx.com.u_life.presentation.screens.owner_screens.profile.ProfileScreen
import mx.com.u_life.presentation.screens.owner_screens.home.OwnerHomeScreen

fun NavGraphBuilder.ownerRoutes(navController: NavController) {
    composable(OwnerRoutes.OWNER_HOME.name) { OwnerHomeScreen(navController) }
    composable(OwnerRoutes.OWNER_ADD_PROPERTY.name) { AddPropertyScreen(navController) }
    composable(OwnerRoutes.OWNER_PROFILE.name) { ProfileScreen(navController) }
    composable(OwnerRoutes.OWNER_INBOX.name) { OwnerInboxScreen(navController) }
    composable(OwnerRoutes.OWNER_CHAT.name) { OwnerChatScreen(navController) }
}