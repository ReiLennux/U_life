package mx.com.u_life.presentation.screens.owner_screens.addproperty.content

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mx.com.u_life.core.constants.Constants.USER_NAME
import mx.com.u_life.domain.models.Response
import mx.com.u_life.domain.models.properties.PropertyTypeModel
import mx.com.u_life.domain.models.rents.LocationModel
import mx.com.u_life.domain.models.rents.TemporalRentModel
import mx.com.u_life.domain.useCases.catalogs.CatalogsUseCases
import mx.com.u_life.domain.useCases.dataStore.DataStoreUseCases
import mx.com.u_life.domain.useCases.rents.RentsUseCases
import mx.com.u_life.presentation.utils.Validations
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddPropertyViewModel @Inject constructor(
    application: Application,
    private val propertiesTypeUseCases: CatalogsUseCases,
    private val _rentUseCases: RentsUseCases,
    private val _validations: Validations,
    private val _fireAuth: FirebaseAuth,
    private val _dataStoreUseCases: DataStoreUseCases
): ViewModel() {
    // Flow
    private val _isLoading = MutableStateFlow<Response<Boolean>?>(value = null)
    val isLoading: MutableStateFlow<Response<Boolean>?> = _isLoading

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    // types
    private val _types = MutableLiveData<List<PropertyTypeModel>>()
    val types: LiveData<List<PropertyTypeModel>> = _types

    private val _typesResponse = MutableStateFlow<Response<List<PropertyTypeModel>>?>(value = null)
    val typesResponse: MutableStateFlow<Response<List<PropertyTypeModel>>?> = _typesResponse

    // Variables
    private val _propertyName = MutableLiveData("")
    val propertyName: LiveData<String> = _propertyName

    private val _propertyDescription = MutableLiveData("")
    val propertyDescription: LiveData<String> = _propertyDescription

    private val _propertyPrice = MutableLiveData("")
    val propertyPrice: LiveData<String> = _propertyPrice

    private val _propertyImages = MutableLiveData<List<Uri>>(emptyList())

    private val _propertyRestrictions = MutableLiveData<List<String>>(emptyList())
    val propertyRestrictions: LiveData<List<String>> = _propertyRestrictions

    private val _propertyFacilities = MutableLiveData<List<String>>(emptyList())
    val propertyFacilities: LiveData<List<String>> = _propertyFacilities


    private val _propertyLatitude = MutableLiveData(0.0)

    private val _propertyLongitude = MutableLiveData(0.0)

    private val _propertyAddress = MutableLiveData("")
    val propertyAddress: LiveData<String> = _propertyAddress

    private val _propertyType = MutableLiveData("")
    val propertyType: LiveData<String> = _propertyType

    // Definiciones para errores
    private val _propertyNameError = MutableLiveData<String?>(null)
    val propertyNameError: LiveData<String?> = _propertyNameError

    private val _propertyDescriptionError = MutableLiveData<String?>(null)
    val propertyDescriptionError: LiveData<String?> = _propertyDescriptionError

    private val _propertyPriceError = MutableLiveData<String?>(null)

    private val _propertyImagesError = MutableLiveData<String?>(null)
    val propertyImagesError: LiveData<String?> = _propertyImagesError

    private val _propertyRestrictionsError = MutableLiveData<String?>(null)
    val propertyRestrictionsError: LiveData<String?> = _propertyRestrictionsError

    private val _propertyFacilitiesError = MutableLiveData<String?>(null)
    val propertyFacilitiesError: LiveData<String?> = _propertyFacilitiesError

    private val _propertyAddressError = MutableLiveData<String?>(null)
    val propertyAddressError: LiveData<String?> = _propertyAddressError

    private val _propertyLocationError = MutableLiveData<String?>(null)

    private val _propertyTypeError = MutableLiveData<String?>(null)
    val propertyTypeError: LiveData<String?> = _propertyTypeError

    init {
        getTypes()
    }

    fun onEvent(event: AddPropertyFormEvent) {
        when (event) {
            is AddPropertyFormEvent.PropertyNameChanged -> {
                _propertyName.value = event.propertyName
            }

            is AddPropertyFormEvent.PropertyDescriptionChanged -> {
                _propertyDescription.value = event.propertyDescription
            }

            is AddPropertyFormEvent.PropertyPriceChanged -> {
                _propertyPrice.value = event.propertyPrice
            }

            is AddPropertyFormEvent.PropertyImagesChanged -> {
                _propertyImages.value = event.propertyImages
            }

            is AddPropertyFormEvent.PropertyRestrictionsChanged -> {
                _propertyRestrictions.value = event.propertyRestrictions
            }

            is AddPropertyFormEvent.PropertyFacilitiesChanged -> {
                _propertyFacilities.value = event.propertyFacilities
            }

            is AddPropertyFormEvent.PropertyLocationChanged -> {
                _propertyLatitude.value = event.propertyLatitude
                _propertyLongitude.value = event.propertyLongitude
            }

            is AddPropertyFormEvent.PropertyTypeChanged -> {
                _propertyType.value = event.propertyType
            }

            is AddPropertyFormEvent.PropertyAddressChanged -> {
                _propertyAddress.value = event.propertyAddress
            }

            is AddPropertyFormEvent.Submit -> {
                submitData()
            }
        }
    }

    suspend fun fetchLocation(): Location? {
        viewModelScope.async {
            try {
                val location = fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY, null
                ).await()

                if (location != null) {
                    return@async location
                } else {

                }
            } catch (e: SecurityException) {
                return@async null
            } catch (e: Exception) {
                return@async null
            }
        }.await()
        return null
    }

    private fun submitData() {
        val propertyNameResult = _validations.validateBasicText(_propertyName.value!!)
        val propertyDescriptionResult = _validations.validateBasicText(_propertyDescription.value!!)
        val propertyPriceResult = _validations.validatePrice(_propertyPrice.value!!)
        val propertyImageResult = _validations.validateImagesList(_propertyImages.value!!)
        val propertyRestrictionsResult = _validations.validateList(_propertyRestrictions.value!!)
        val propertyFacilitiesResult = _validations.validateList(_propertyFacilities.value!!)
        val propertyAddressResult = _validations.validateBasicText(_propertyAddress.value!!)
        val propertyTypeResult = _validations.validateComboBox(_propertyType.value!!)
        val propertyLocationResult = _validations.validateLocation(_propertyLatitude.value!!, _propertyLongitude.value!!)



        val hasError = listOf(
            propertyNameResult,
            propertyDescriptionResult,
            propertyPriceResult,
            propertyImageResult,
            propertyRestrictionsResult,
            propertyFacilitiesResult,
            propertyAddressResult,
            propertyTypeResult
        ).any { !it.successful }

        if (hasError) {
            _propertyNameError.value = propertyNameResult.errorMessage
            _propertyDescriptionError.value = propertyDescriptionResult.errorMessage
            _propertyPriceError.value = propertyPriceResult.errorMessage
            _propertyImagesError.value = propertyImageResult.errorMessage
            _propertyRestrictionsError.value = propertyRestrictionsResult.errorMessage
            _propertyFacilitiesError.value = propertyFacilitiesResult.errorMessage
            _propertyTypeError.value = propertyTypeResult.errorMessage
            _propertyLocationError.value = propertyLocationResult.errorMessage
            _propertyAddressError.value = propertyAddressResult.errorMessage

            return
        }
        viewModelScope.launch {
            postProperty()
        }

    }

    private suspend fun postProperty() = viewModelScope.async {
        val temporalRent = TemporalRentModel  (
                ownerId = _fireAuth.currentUser!!.uid,
                ownerName = _dataStoreUseCases.getDataString.invoke(USER_NAME),
                name = _propertyName.value!!,
                description = _propertyDescription.value!!,
                price = _propertyPrice.value!!.toInt(),
                images = _propertyImages.value!!.map { it },
                restrictions = _propertyRestrictions.value!!,
                services = _propertyFacilities.value!!,
                location = LocationModel(
                    latitude = _propertyLatitude.value!!,
                    longitude = _propertyLongitude.value!!,
                    address = _propertyAddress.value!!
                ),
                type = _propertyType.value!!
        )
        _isLoading.value = Response.Loading
        val response = _rentUseCases.postRent.invoke(rent = temporalRent)
        _isLoading.value = response
//        if (response is Response.Success){
//            _isLoading.value = Response.Success(true)
//        }else {
//            _isLoading.value = Response.Error(Exception("Error"))
//        }
    }.await()

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

    fun resetInitialState() {
        _typesResponse.value = null
        _isLoading.value = null
    }


    class LocationHelper(private val context: Context) {
        fun getAddressFromLocation(location: Location): String? {
            return try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                println(addresses)
                if (addresses!!.isNotEmpty()) {
                    addresses[0].getAddressLine(0)
                } else {
                    "Dirección no disponible"
                }
            } catch (e: Exception) {
                Log.e("LocationHelper", "Error obteniendo dirección", e)
                null
            }
        }

        private var fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)

        @SuppressLint("MissingPermission")
        suspend fun getLastKnownLocation(): Location? {
            return try {
                fusedLocationClient.lastLocation.await()
            } catch (e: Exception) {
                null
            }
        }

        @SuppressLint("MissingPermission")
        fun requestCurrentLocation(
            locationRequest: LocationRequest,
            callback: (Location) -> Unit
        ) {
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.locations.firstOrNull()?.let { location ->
                        callback(location)
                        fusedLocationClient.removeLocationUpdates(this)
                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }

        fun hasLocationPermission(): Boolean {
            return ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}