package mx.com.u_life.presentation.navigation

sealed class NavigationScreens(val screen: String) {
    data object Home: NavigationScreens("home")
    data object Chats: NavigationScreens("chats")
    data object Profile: NavigationScreens("profile")
}