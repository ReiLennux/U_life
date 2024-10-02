package mx.com.u_life.presentation.screens.home.content

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {


    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)

    private val _location = MutableLiveData<Location?>()
    val location: LiveData<Location?> = _location

    init {
        fetchLocation()
    }

    fun updateLocation(newLocation: Location?) {
        _location.value = newLocation
    }

    fun fetchLocation() {
        viewModelScope.launch {
            try {
                val locationResult = fusedLocationClient.lastLocation
                locationResult.addOnSuccessListener { location ->
                    _location.value = location
                }
                locationResult.addOnFailureListener {
                    _location.value = null
                }
            } catch (e: SecurityException) {
                _location.value = null
            }
        }
    }
}