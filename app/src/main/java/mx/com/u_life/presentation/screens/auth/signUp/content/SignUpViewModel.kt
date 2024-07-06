package mx.com.u_life.presentation.screens.auth.signUp.content

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
class SignUpViewModel @Inject constructor(
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

    private val _confirmPassword = MutableLiveData("")
    val confirmPassword: LiveData<String> = _confirmPassword

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    // Definiciones para errores
    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError

    private val _repeatedPasswordError = MutableLiveData<String?>()
    val repeatedPasswordError: LiveData<String?> = _repeatedPasswordError

    private val _nameError = MutableLiveData<String?>()
    val nameError: LiveData<String?> = _nameError

    fun resetInitState(){
        _isLoading.value = null
    }

    fun onEvent(event: SignUpFormEvent){
        when(event){
            is SignUpFormEvent.EmailChanged -> {
                _email.value = event.email
            }
            is SignUpFormEvent.PasswordChanged -> {
                _password.value = event.password
            }
            is SignUpFormEvent.RepeatedPasswordChanged -> {
                _confirmPassword.value = event.repeatedPassword
            }
            is SignUpFormEvent.NameChanged -> {
                _name.value = event.name
            }
            is SignUpFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = _validations.validateEmail(_email.value!!)
        val passwordResult = _validations.validateStrongPassword(_password.value!!)
        val repeatedPasswordResult = _validations.validateRepeatedPassword(_password.value!!, _confirmPassword.value!!)
        val nameResult = _validations.validateBasicText(_name.value!!)

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult,
            nameResult
        ).any{ !it.successful }

        if (hasError){
            _emailError.value = emailResult.errorMessage
            _passwordError.value = passwordResult.errorMessage
            _repeatedPasswordError.value = repeatedPasswordResult.errorMessage
            _nameError.value = nameResult.errorMessage
            return
        }
        viewModelScope.launch {
            signUpUser()
        }
    }

    private suspend fun signUpUser() = viewModelScope.async {
        _isLoading.value = Response.Loading
        val registerValue = _fireAuthUseCases.registerUser(_email.value!!, _password.value!!, _name.value!!)
        if (registerValue) {
            _isLoading.value = Response.Success(true)
        } else {
            _isLoading.value = Response.Error(Exception("Error"))
        }
    }.await()

}