package mx.com.u_life.presentation.screens.auth.signUp.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import mx.com.u_life.domain.models.Response
import mx.com.u_life.domain.useCases.dataStore.DataStoreUseCases
import mx.com.u_life.domain.useCases.fireAuth.FireAuthUseCases
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val _fireAuthUseCases: FireAuthUseCases,
    private val _dataStoreUseCases: DataStoreUseCases
): ViewModel() {
    // Flow
    private val _isLoading = MutableStateFlow<Response<Boolean>?>(value = null)
    val isLoading: MutableStateFlow<Response<Boolean>?> = _isLoading

    // Variables
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _confirmPassword = MutableLiveData("")
    val confirmPassword: LiveData<String> = _confirmPassword

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    fun resetInitState(){
        _isLoading.value = null
    }

    fun onChanged(email: String, password: String, confirmPassword: String, name: String){
        _email.value = email
        _password.value = password
        _confirmPassword.value = confirmPassword
        _name.value = name
    }

}