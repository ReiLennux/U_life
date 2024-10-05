package mx.com.u_life.presentation.screens.profile.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mx.com.u_life.domain.useCases.dataStore.DataStoreUseCases
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val _dataStoreUseCases: DataStoreUseCases,
    private val _fireAuth: FirebaseAuth
): ViewModel() {
    private val _userName = MutableLiveData("")
    val userName: LiveData<String> = _userName

    private val _userType = MutableLiveData("")
    val userType: LiveData<String> = _userType

    init{
        setUserInfo()
    }

    private fun setUserInfo() {
        viewModelScope.launch {
            _userName.value = _dataStoreUseCases.getDataString("user_name")
            _userType.value = _dataStoreUseCases.getDataString("userType")
        }
    }

    fun logOut(){
        viewModelScope.launch {
            _dataStoreUseCases.setDataString.invoke("user_name", "")
            _dataStoreUseCases.setDataString.invoke("userType", "")
            _fireAuth.signOut()
        }
    }
}