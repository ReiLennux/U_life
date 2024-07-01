package mx.com.u_life.presentation.screens.auth.login.content

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mx.com.u_life.domain.useCases.dataStore.DataStoreUseCases
import mx.com.u_life.domain.useCases.fireAuth.FireAuthUseCases
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val _fireAuthUseCases: FireAuthUseCases,
    private val _dataStoreUseCases: DataStoreUseCases
) {
    // Flow
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Variables
    private val _email = MutableLiveData("")
    val email: MutableLiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: MutableLiveData<String> = _password

}