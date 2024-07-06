package mx.com.u_life.presentation.screens.auth.login.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import mx.com.u_life.domain.models.Response
import mx.com.u_life.domain.useCases.dataStore.DataStoreUseCases
import mx.com.u_life.domain.useCases.firebase.FireAuthUseCases
import mx.com.u_life.presentation.enums.Routes
import mx.com.u_life.presentation.utils.Validations
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val _fireAuthUseCases: FireAuthUseCases,
    private val _dataStoreUseCases: DataStoreUseCases,
    private val _validations: Validations,
): ViewModel() {
    // Flow
    private val _isLoading = MutableStateFlow<Response<Boolean>?>(value = null)
    val isLoading: MutableStateFlow<Response<Boolean>?> = _isLoading

    // Variables
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    // Errors messages
    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError

    fun resetInitState(){
        _isLoading.value = null
    }

    fun onEvent(event: LoginFormEvent){
        when(event){
            is LoginFormEvent.EmailChanged -> {
                _email.value = event.email
            }
            is LoginFormEvent.PasswordChanged -> {
                _password.value = event.password
            }
            is LoginFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = _validations.validateEmail(_email.value!!)
        val passwordResult = _validations.validateStrongPassword(_password.value!!)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        if (hasError){
            _emailError.value = emailResult.errorMessage
            _passwordError.value = passwordResult.errorMessage
            return
        }

        viewModelScope.launch {
            loginUser()
        }
    }

    private suspend fun loginUser() = viewModelScope.async {
        _isLoading.value = Response.Loading
        val registerValue = _fireAuthUseCases.loginUser(_email.value!!, _password.value!!)
        if (registerValue) {
            _isLoading.value = Response.Success(true)
        } else {
            _isLoading.value = Response.Error(Exception("Error"))
        }
    }.await()

}