package mx.com.u_life.presentation.screens.home.content

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mx.com.u_life.domain.models.Response
import mx.com.u_life.domain.models.rents.RentLocationModel
import mx.com.u_life.domain.models.rents.RentModel
import mx.com.u_life.domain.useCases.rents.RentsUseCases
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val _rentsUseCases: RentsUseCases,
    @ApplicationContext private val context: Context
) : AndroidViewModel(application) {

    val types = mapOf(
        0 to "Todos",
        1 to "Cuarto",
        2 to "Departamento",
        3 to "Casa"
    )

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)

    private val _rentsResponse = MutableStateFlow<Response<List<RentLocationModel>>?>(value = null)
    val rentsResponse: StateFlow<Response<List<RentLocationModel>>?> = _rentsResponse

    private val _selectedType = MutableStateFlow("Todos")
    val selectedType: StateFlow<String> = _selectedType

    private val _rentDetailResponse = MutableStateFlow<Response<RentModel>?>(value = null)
    val rentDetailResponse: StateFlow<Response<RentModel>?> = _rentDetailResponse

    private val _location = MutableLiveData<Location?>()
    val location: LiveData<Location?> = _location

    private val _rents = MutableLiveData<List<RentLocationModel>>()
    val rents: LiveData<List<RentLocationModel>> = _rents
    private var _rentsReady = false

    private val _rentDetails = MutableLiveData<RentModel?>()
    val rentDetails: LiveData<RentModel?> = _rentDetails

    private val _bottomSheetEnabled = MutableLiveData<Boolean>()
    val bottomSheetEnabled: LiveData<Boolean> = _bottomSheetEnabled

    private val _isAllReady = MutableStateFlow(false)
    val isAllReady = _isAllReady.asStateFlow()

    private val _isRentDetailObtained = MutableLiveData(false)
    val isRentDetailObtained = _isRentDetailObtained

    init {
        viewModelScope.launch {
            fetchLocation()
            getAllRents()
            checkAllReady()
        }
    }

    private fun checkAllReady() {
        if (_rentsReady) {
            _isAllReady.value = true
        }
    }

    suspend fun fetchLocation() {
        viewModelScope.async {
            try {
                val location = fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY, null
                ).await()

                if (location != null){
                    _location.value = location
                }
            } catch (e: SecurityException) {
                _location.value = null
            } catch (e: Exception) {
                _location.value = null
            }
        }.await()
    }

    private suspend fun getAllRents() = viewModelScope.async {
        _rentsResponse.value = Response.Loading
        val response = _rentsUseCases.getAllRents()
        _rentsResponse.value = response
        _rentsReady = true
    }.await()

    fun filterTypes(value: String){
        _selectedType.value = value
    }

    fun assignRentsResult(rents: List<RentLocationModel>) {
        _rents.value = rents
    }

    fun resetRents() {
        _rentsResponse.value = null
    }

    fun resetRentDetails() {
        _rentDetailResponse.value = null
    }

    fun resizeMarkerIcon(resourceId: Int, width: Int, height: Int): BitmapDescriptor {
        val imageBitmap = BitmapFactory.decodeResource(context.resources, resourceId)
        val resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false)
        return BitmapDescriptorFactory.fromBitmap(resizedBitmap)
    }

    private fun getRentDetails(rentId: String) = viewModelScope.launch {
        _rentDetailResponse.value = Response.Loading
        val response = _rentsUseCases.getRentDetails(rentId)
        _rentDetailResponse.value = response
    }

    fun assignRentDetails(rents: RentModel) {
        _rentDetails.value = rents
        _isRentDetailObtained.value = true
    }

    fun enableBottomSheet(value: Boolean, rentId: String = "") {
        _bottomSheetEnabled.value = value

        if (rentId.isNotEmpty()) {
            getRentDetails(rentId)
        }

        if (!value) {
            _rentDetailResponse.value = null
            _rentDetails.value = null
            _isRentDetailObtained.value = false
        }
    }

}