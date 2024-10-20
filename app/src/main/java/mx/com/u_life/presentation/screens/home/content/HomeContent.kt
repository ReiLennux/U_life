package mx.com.u_life.presentation.screens.home.content

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import mx.com.u_life.R
import mx.com.u_life.domain.models.rents.RentLocationModel
import mx.com.u_life.presentation.components.GenericCard
import mx.com.u_life.presentation.components.RentDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val rents by viewModel.rents.observeAsState(initial = emptyList())

    val isBottomSheetVisible by viewModel.bottomSheetEnabled.observeAsState(initial = false)
    val rent by viewModel.rentDetails.observeAsState()
    val isRentDetailsObtained by viewModel.isRentDetailObtained.observeAsState(false)

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(isBottomSheetVisible) {
        if (isBottomSheetVisible) {
            sheetState.show()
        } else {
            sheetState.hide()
        }
    }

    HomeDesign(
        modifier = modifier,
        viewModel = viewModel,
        context = context,
        rents = rents
    )

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    viewModel.enableBottomSheet(false)
                }
            },
            sheetState = sheetState,
            content = {
                RentDetails(rent = rent, isRentDetail = isRentDetailsObtained)
            }
        )
    }
}


@Composable
fun HomeDesign(
    modifier: Modifier,
    viewModel: HomeViewModel,
    context: Context,
    rents: List<RentLocationModel>
) {
    val scope = rememberCoroutineScope()
    // Verificar permisos
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Launcher para solicitar
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasLocationPermission = isGranted
        if (isGranted) {
            scope.launch {
                viewModel.fetchLocation()
            }
        } else {
            Log.d("HomeContent", "Permiso de ubicación denegado")
        }
    }

    // Solicitar permisos si no se tienen
    LaunchedEffect(hasLocationPermission) {
        if (!hasLocationPermission) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //mostrar el mapa
            if (hasLocationPermission) {
                MapView(rents)
            } else {
                // Mostrar pantalla para otorgar permisos
                UbicationPermissions(locationPermissionLauncher = locationPermissionLauncher)
            }
        }
    }
}

@Composable
fun UbicationPermissions(
    locationPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>
) {
    GenericCard(
        title = R.string.permissions_location_title,
        description = R.string.permissions_location
    ) {
        Button(
            onClick = {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        ) {
            Text(text = "Otorgar permisos")
        }
    }
}



@Composable
fun MapView(
    rents: List<RentLocationModel> = emptyList(),
    viewModel: HomeViewModel = hiltViewModel()
) {
    val location by viewModel.location.observeAsState(initial = null)

    val defaultPosition = LatLng(23.6345, -102.5528)
    val userPosition = LatLng(location?.latitude ?: defaultPosition.latitude, location?.longitude ?: defaultPosition.longitude)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultPosition, 5f)
    }

    LaunchedEffect(location) {
        location?.let {
            val newUserPosition = LatLng(it.latitude, it.longitude)
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(newUserPosition, 15f),
                durationMs = 1000
            )
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(userPosition),
            title = "Mi ubicación",
            icon = viewModel.resizeMarkerIcon(R.drawable.user_marker, 200, 200),
        )

        rents.forEach { rent ->
            val rentPosition = LatLng(rent.latitude, rent.longitude)

            Marker(
                state = MarkerState(rentPosition),
                title = rent.name,
                icon = viewModel.resizeMarkerIcon(R.drawable.rent_marker, 200, 200),
                onClick = {
                    viewModel.enableBottomSheet(value = true, rentId = rent.id)
                    true
                }
            )
        }
    }
}