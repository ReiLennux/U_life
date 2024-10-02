package mx.com.u_life.presentation.screens.home.content

import android.Manifest
import android.content.pm.PackageManager
import android.preference.PreferenceManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mx.com.u_life.R
import mx.com.u_life.presentation.components.DialogWithIcon
import mx.com.u_life.presentation.components.GenericCard
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val location by viewModel.location.observeAsState(initial = null)

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
            viewModel.fetchLocation()
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

    Box(modifier = modifier.padding(paddingValues = paddingValues)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //mostrar el mapa
            if (hasLocationPermission) {
                location?.let { loc ->
                    OsmdroidMapView(loc.latitude, loc.longitude)
                } ?: run {
                    Text(text = "Obteniendo ubicación...")
                }
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
fun OsmdroidMapView(
    latitud : Double,
    longitud : Double
) {
    val context = LocalContext.current

    Configuration.getInstance()
        .load(context, PreferenceManager.getDefaultSharedPreferences(context))
    Configuration.getInstance().userAgentValue = "MapApp"

    AndroidView(modifier = Modifier.fillMaxSize(), factory = {
        val mapView = MapView(context)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)
        // Centrar el mapa
        val mapController = mapView.controller
        mapController.setZoom(17.0)
        mapController.setCenter(GeoPoint(latitud, longitud))

        val geoPoint = GeoPoint(latitud, longitud)
        val startMarker = Marker(mapView)
        startMarker.position = geoPoint
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        mapView.overlays.add(startMarker)
        mapView
    })
}
