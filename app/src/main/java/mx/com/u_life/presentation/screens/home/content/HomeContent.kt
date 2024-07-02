package mx.com.u_life.presentation.screens.home.content

import android.preference.PreferenceManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val location by viewModel.location.observeAsState(initial = null)

    Box(
        modifier = modifier.padding(paddingValues = paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            location?.let { loc ->
                OsmdroidMapView(loc.latitude, loc.longitude)
            }// ?: run {
             //   Text(text = "Obteniendo ubicación...")
            //}
        }
    }
}

@Composable
fun OsmdroidMapView(latitud: Double, longitud: Double) {
    val context = LocalContext.current

    Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
    Configuration.getInstance().userAgentValue = "MapApp"

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
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
        }
    )
}
