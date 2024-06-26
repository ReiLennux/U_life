package mx.com.u_life.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import mx.com.u_life.presentation.enums.Routes

@Composable
fun BottomNavBar(navController: NavHostController) {

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        navigationItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                onClick = {
                    if (navController.currentDestination?.route != navItem.route) {
                        navController.navigate(navItem.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                label = { Text(text = navItem.title) },
                alwaysShowLabel = true,
                icon = { BadgedBox(
                    badge = {
                        if (navItem.hasBadge != null){
                            Badge { Text(text = navItem.hasBadge.toString()) }
                        } else if (navItem.hasNews) {
                            Badge()
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (currentDestination?.hierarchy?.any { it.route == navItem.route } == true) {
                            navItem.selectedIcon
                        } else navItem.unselectedIcon, contentDescription = navItem.title)
                } },
            )
        }
    }

}

@Composable
fun TopAppBar() {
    /*TODO: Lenn*/
}

data class NavigationItems(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val hasBadge: Int? = null
)

val navigationItems = listOf(
    NavigationItems(
        title = "Home",
        route = Routes.HOME.name,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        hasNews = false
    ),
    NavigationItems(
        title = "Chats",
        route = Routes.CHATS.name,
        selectedIcon = Icons.Filled.ChatBubble,
        unselectedIcon = Icons.Outlined.ChatBubbleOutline,
        hasNews = false,
        hasBadge = null
    ),
    NavigationItems(
        title = "Perfil",
        route = Routes.PROFILE.name,
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        hasNews = false
    )
)