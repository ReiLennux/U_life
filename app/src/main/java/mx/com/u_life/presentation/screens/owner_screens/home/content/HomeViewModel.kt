package mx.com.u_life.presentation.screens.owner_screens.home.content

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import mx.com.u_life.domain.models.Response
import mx.com.u_life.domain.models.rents.RentModel
import mx.com.u_life.domain.useCases.rents.RentsUseCases
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val _rentUseCases: RentsUseCases,
    private val _fireAuth: FirebaseAuth,
) : ViewModel() {

    // Flow
    private val _propertiesResponse = MutableStateFlow<Response<List<RentModel>>?>(value = null)
    val propertiesResponse: MutableStateFlow<Response<List<RentModel>>?> = _propertiesResponse

    private val _properties = MutableStateFlow<List<RentModel>>(emptyList())
    val properties: MutableStateFlow<List<RentModel>> = _properties

    init{
        getMyProperties()
    }

    private fun getMyProperties() {
        val userId = _fireAuth.currentUser!!.uid
        viewModelScope.launch {
            _propertiesResponse.value = Response.Loading
            val response = _rentUseCases.getMyRents.invoke(userId)
            _propertiesResponse.value = response
        }
    }

    fun assingPropertiesInfo(properties: List<RentModel>){
        _properties.value = properties
    }

    fun resetInitialState(){
        _propertiesResponse.value = null
    }
}