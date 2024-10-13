package mx.com.u_life.presentation.screens.owner_screens.profile.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mx.com.u_life.core.constants.Constants.USER_EMAIL
import mx.com.u_life.core.constants.Constants.USER_NAME
import mx.com.u_life.core.constants.Constants.USER_TYPE
import mx.com.u_life.core.constants.Constants.USER_UID
import mx.com.u_life.domain.useCases.dataStore.DataStoreUseCases
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val _dataStoreUseCases: DataStoreUseCases,
    private val _fireAuth: FirebaseAuth
): ViewModel(){
    private val _userName = MutableLiveData("")
    val userName: LiveData<String> = _userName

    private val _userType = MutableLiveData("")
    val userType: LiveData<String> = _userType

    init{
        setUserInfo()
    }

    private fun setUserInfo() {
        viewModelScope.launch {
            _userName.value = _dataStoreUseCases.getDataString(USER_NAME)
            _userType.value = _dataStoreUseCases.getDataString(USER_TYPE)
        }
    }

    fun logOut(){
        viewModelScope.launch {
            _dataStoreUseCases.setDataString.invoke(USER_TYPE, "")
            _dataStoreUseCases.setDataString.invoke(USER_UID, "")
            _dataStoreUseCases.setDataString.invoke(USER_NAME, "")
            _dataStoreUseCases.setDataString.invoke(USER_EMAIL, "")
            _fireAuth.signOut()
        }
    }
}