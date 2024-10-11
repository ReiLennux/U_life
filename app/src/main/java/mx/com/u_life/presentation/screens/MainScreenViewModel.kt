package mx.com.u_life.presentation.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.com.u_life.core.constants.Constants.USER_EMAIL
import mx.com.u_life.core.constants.Constants.USER_NAME
import mx.com.u_life.core.constants.Constants.USER_TYPE
import mx.com.u_life.core.constants.Constants.USER_UID
import mx.com.u_life.domain.models.Response
import mx.com.u_life.domain.models.users.UserModel
import mx.com.u_life.domain.useCases.dataStore.DataStoreUseCases
import mx.com.u_life.domain.useCases.userUseCases.UserUseCases
import mx.com.u_life.presentation.enums.Routes
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val _fireAuth: FirebaseAuth,
    private val _userUseCases: UserUseCases,
    private val _dataStoreUseCases: DataStoreUseCases
): ViewModel() {

    //Variables
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _currentUser = MutableLiveData<UserModel?>()
    val currentUser: MutableLiveData<UserModel?> = _currentUser

    private val _userResponse = MutableStateFlow<Response<UserModel>?>(value = null)
    val userResponse: MutableStateFlow<Response<UserModel>?> = _userResponse

    // Init
    init {
        verifyUser()
    }

    //Functions
    private fun verifyUser() {
        _fireAuth.addAuthStateListener { firebaseAuth ->
            _isLoggedIn.value = firebaseAuth.currentUser != null
            if (_isLoggedIn.value) {
                getCurrentUser()
            }else {
                _currentUser.value = UserModel (
                    name = "",
                    email = "",
                    userType = "student"
                )
            }
        }
    }

    private fun getCurrentUser() = viewModelScope.launch {
        _userResponse.value = Response.Loading
        val response = _userUseCases.getUser.invoke(userId = _fireAuth.currentUser!!.uid)
        _userResponse.value = response
        if (response is Response.Success){
            setDataStoreInfo(response.data)
        }
    }

    private fun setDataStoreInfo(user: UserModel) = viewModelScope.launch {
        _dataStoreUseCases.setDataString.invoke(USER_TYPE, user.userType)
        _dataStoreUseCases.setDataString.invoke(USER_UID, user.id)
        _dataStoreUseCases.setDataString.invoke(USER_NAME, user.name)
        _dataStoreUseCases.setDataString.invoke(USER_EMAIL, user.email)
    }

    fun resetInitialState(){
        _userResponse.value = null
    }

    fun assignCurrentUser(currentUser: UserModel){
        _currentUser.value = currentUser
    }


    fun verifyRouteTop(currentRoute: String?): Boolean {
        return !(currentRoute == Routes.HOME.name || currentRoute == Routes.LOGIN.name || currentRoute == Routes.SIGN_UP.name)
    }

    fun verifyRouteBottom(currentRoute: String?): Boolean {
        return !(currentRoute == Routes.LOGIN.name || currentRoute == Routes.SIGN_UP.name)
    }
}