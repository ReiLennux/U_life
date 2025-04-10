package mx.com.u_life.presentation.screens.tenant_screens.home.content

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import androidx.compose.ui.graphics.Color
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
import mx.com.u_life.domain.models.properties.PropertyTypeModel
import mx.com.u_life.domain.models.rents.RentLocationModel
import mx.com.u_life.domain.models.rents.RentModel
import mx.com.u_life.domain.useCases.catalogs.CatalogsUseCases
import mx.com.u_life.domain.useCases.rents.RentsUseCases
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val _rentsUseCases: RentsUseCases,
    private val propertiesTypeUseCases: CatalogsUseCases,
    @ApplicationContext private val context: Context
) : AndroidViewModel(application) {

    private val _types = MutableLiveData<List<PropertyTypeModel>>()
    val types: LiveData<List<PropertyTypeModel>> = _types

    private val _typesResponse = MutableStateFlow<Response<List<PropertyTypeModel>>?>(value = null)
    val typesResponse: MutableStateFlow<Response<List<PropertyTypeModel>>?> = _typesResponse

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)

    private val _rentsResponse = MutableStateFlow<Response<List<RentLocationModel>>?>(value = null)
    val rentsResponse: StateFlow<Response<List<RentLocationModel>>?> = _rentsResponse

    private val _selectedType = MutableLiveData<PropertyTypeModel?>(null)
    val selectedType: LiveData<PropertyTypeModel?> = _selectedType

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
            getTypes()
        }
    }

    private fun getTypes() {
        viewModelScope.launch {
            _typesResponse.value = Response.Loading
            val response = propertiesTypeUseCases.getPropertiesType.invoke()
            _typesResponse.value = response
        }
    }

    fun assignTypes(types: List<PropertyTypeModel>) {
        _types.value = types
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

    fun filterTypes(type: PropertyTypeModel?){
        _selectedType.value = type
    }

    fun assignRentsResult(rents: List<RentLocationModel>) {
        _rents.value = rents
    }

    fun resetRents() {
        _rentsResponse.value = null
    }

    fun resetTypes() {
        _typesResponse.value = null
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


    //Map Theme

    private fun Color.toHexString(): String {
        val alpha = (alpha * 255).toInt()
        val red = (red * 255).toInt()
        val green = (green * 255).toInt()
        val blue = (blue * 255).toInt()
        return String.format("#%02X%02X%02X%02X", alpha, red, green, blue)
    }

    fun generateMapStyle(primaryColor: Color, backgroundColor: Color, textColor: Color): String {
        return """
    [
      {
        "elementType": "geometry",
        "stylers": [
          { "color": "${backgroundColor.toHexString()}" }
        ]
      },
      {
        "elementType": "labels.text.fill",
        "stylers": [
          { "color": "${textColor.toHexString()}" }
        ]
      },
      {
        "elementType": "labels.text.stroke",
        "stylers": [
          { "color": "${backgroundColor.copy(alpha = 0.9f).toHexString()}" },
          { "weight": 2 }
        ]
      },
      {
        "featureType": "road",
        "elementType": "geometry",
        "stylers": [
          { "color": "${primaryColor.copy(alpha = 0.9f).toHexString()}" }
        ]
      },
      {
        "featureType": "road",
        "elementType": "geometry.stroke",
        "stylers": [
          { "color": "${primaryColor.copy(alpha = 0.6f).toHexString()}" },
          { "weight": 1.5 }
        ]
      },
      {
        "featureType": "road",
        "elementType": "labels.text.fill",
        "stylers": [
          { "color": "${textColor.copy(alpha = 0.95f).toHexString()}" }
        ]
      },
      {
        "featureType": "road",
        "elementType": "labels.text.stroke",
        "stylers": [
          { "color": "${backgroundColor.copy(alpha = 0.85f).toHexString()}" },
          { "weight": 2 }
        ]
      },
      {
        "featureType": "poi",
        "stylers": [
          { "visibility": "off" }
        ]
      },
      {
        "featureType": "transit",
        "stylers": [
          { "visibility": "off" }
        ]
      },
      {
        "featureType": "water",
        "elementType": "geometry",
        "stylers": [
          { "color": "${primaryColor.copy(alpha = 0.3f).toHexString()}" }
        ]
      }
    ]
    """.trimIndent()
    }

}