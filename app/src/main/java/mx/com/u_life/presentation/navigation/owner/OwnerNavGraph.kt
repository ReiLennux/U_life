package mx.com.u_life.presentation.navigation.owner

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.com.u_life.presentation.enums.OwnerRoutes
import mx.com.u_life.presentation.screens.owner_screens.addproperty.AddPropertyScreen
import mx.com.u_life.presentation.screens.owner_screens.home.HomeScreen

fun NavGraphBuilder.ownerRoutes(navController: NavController) {
    composable(OwnerRoutes.OWNER_HOME.name) { HomeScreen(navController) }
    composable(OwnerRoutes.OWNER_ADD_PROPERTY.name) { AddPropertyScreen(navController = navController) }
}