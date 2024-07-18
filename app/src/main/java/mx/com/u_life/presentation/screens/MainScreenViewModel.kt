package mx.com.u_life.presentation.screens

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mx.com.u_life.presentation.enums.Routes
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val _fireAuth: FirebaseAuth
): ViewModel() {
    // Firebase Instance
    val auth = _fireAuth

    //Variables
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    // Init
    init {
        verifyUser()
    }

    //Functions
    private fun verifyUser() {
        auth.addAuthStateListener { firebaseAuth ->
            _isLoggedIn.value = firebaseAuth.currentUser != null
        }
    }

    fun verifyRouteTop(currentRoute: String?): Boolean {
        return !(currentRoute == Routes.HOME.name || currentRoute == Routes.LOGIN.name || currentRoute == Routes.SIGN_UP.name)
    }

    fun verifyRouteBottom(currentRoute: String?): Boolean {
        return !(currentRoute == Routes.LOGIN.name || currentRoute == Routes.SIGN_UP.name)
    }
}