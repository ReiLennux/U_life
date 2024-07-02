package mx.com.u_life.presentation.screens.auth.login.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mx.com.u_life.domain.models.Response
import mx.com.u_life.domain.useCases.dataStore.DataStoreUseCases
import mx.com.u_life.domain.useCases.fireAuth.FireAuthUseCases
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val _fireAuthUseCases: FireAuthUseCases,
    private val _dataStoreUseCases: DataStoreUseCases
): ViewModel() {
    // Flow
    private val _isLoading = MutableStateFlow<Response<Boolean>?>(value = null)
    val isLoading: MutableStateFlow<Response<Boolean>?> = _isLoading

    // Variables
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email
    val emailError = MutableLiveData(false)

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password
    val passwordError = MutableLiveData(false)

    fun resetInitState(){
        _isLoading.value = null
    }

    fun onChanged(email: String, password: String){
        if (email.isEmpty() || password.isEmpty()) {
            emailError.value = _email.value.isNullOrEmpty()
            passwordError.value = _password.value.isNullOrEmpty()
        }
        _email.value = email
        _password.value = password
    }

}