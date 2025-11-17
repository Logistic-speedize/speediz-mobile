package com.example.speediz.ui.feature.appwide.button

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.speediz.BuildConfig
import com.example.speediz.R
import com.example.speediz.ui.feature.authorized.delivery.express.detail.ExpressDetailViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.mapbox.common.MapboxOptions
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.addLayerBelow
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.plugin.locationcomponent.location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import kotlin.math.hypot

//@Composable
//fun MapboxUserRoute(
//    destinationLat: Double,
//    destinationLon: Double,
//    minDistanceMeters: Double = 50.0
//) {
//    val context = LocalContext.current
//    val coroutineScope = rememberCoroutineScope()
//
//    Log.d("MapboxUserRoute" , "Destination: ($destinationLat, $destinationLon)" )
//    // Set access token once
//    MapboxOptions.accessToken = BuildConfig.MAPBOX_ACCESS_TOKEN
//
//    AndroidView(factory = { ctx ->
//        val mapView = MapView(ctx)
//        val mapboxMap = mapView.mapboxMap
//        var lastUserPosition: Point? = null
//        val destinationPoint = Point.fromLngLat(destinationLon, destinationLat)
//
//        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) { style ->
//
//            // Destination marker
//            context.getDrawable(R.drawable.ic_location_on_map)?.toBitmap()?.let {
//                style.addImage("destination_marker", it)
//            }
//            style.addSource(geoJsonSource("destination-source") { geometry(destinationPoint) })
//            style.addLayer(symbolLayer("destination-layer", "destination-source") {
//                iconImage("destination_marker")
//                iconAllowOverlap(true)
//                iconIgnorePlacement(true)
//            })
//
//            // Origin marker
//            context.getDrawable(R.drawable.ic_radio_button_checked)?.toBitmap()?.let {
//                style.addImage("origin_marker", it)
//            }
//            style.addSource(geoJsonSource("origin-source") { geometry(Point.fromLngLat( 0.0, 0.0)) })
//            style.addLayer(symbolLayer("origin-layer", "origin-source") {
//                iconImage("origin_marker")
//                iconAllowOverlap(true)
//                iconIgnorePlacement(true)
//            })
//
//            // Route line
//            style.addSource(geoJsonSource("route-source") {
//                geometry(LineString.fromLngLats(listOf(destinationPoint, destinationPoint)))
//            })
//            style.addLayer(
//                lineLayer("route-layer", "route-source") {
//                    lineColor("#1E90FF") // blue color for navigation route
//                    lineWidth(8.0)        // thicker line
//                    lineCap(LineCap.ROUND) // rounded ends
//                    lineJoin(LineJoin.ROUND) // smooth corners
//                }
//            )
//
//            // Enable location plugin
//            val locationPlugin = mapView.location
//            locationPlugin.updateSettings {
//                enabled = true
//                pulsingEnabled = true
//                locationPuck = LocationPuck2D()
//            }
//
//            // Listen for user movement
//            locationPlugin.addOnIndicatorPositionChangedListener { userPoint ->
//                if (userPoint.latitude() == 0.0 && userPoint.longitude() == 0.0) return@addOnIndicatorPositionChangedListener
//
//                style.getSourceAs<com.mapbox.maps.extension.style.sources.generated.GeoJsonSource>("origin-source")
//                    ?.geometry(userPoint)
//
//                if (lastUserPosition == null || distanceBetween(lastUserPosition!!, userPoint) >= minDistanceMeters) {
//                    lastUserPosition = userPoint
//
//                    coroutineScope.launch(Dispatchers.IO) {
//                        val routeLine = fetchRoute(userPoint, destinationPoint)
//                        routeLine?.let { line ->
//                            coroutineScope.launch(Dispatchers.Main) {
//                                style.getSourceAs<com.mapbox.maps.extension.style.sources.generated.GeoJsonSource>("route-source")
//                                    ?.geometry(line)
//                            }
//                        }
//                    }
//                }
//
//                val centerLng = (userPoint.longitude() + destinationPoint.longitude()) / 2
//                val centerLat = (userPoint.latitude() + destinationPoint.latitude()) / 2
//                val cameraOptions = CameraOptions.Builder()
//                    .center(Point.fromLngLat(centerLng, centerLat))
//                    .zoom(14.0) // target zoom level
//                    .build()
//
//                CoroutineScope(Dispatchers.Main).launch {
//                    delay(500) // delay before moving camera
//                    mapboxMap.easeTo(
//                        cameraOptions,
//                        MapAnimationOptions.Builder().duration(1500).build()
//                    )
//                }
//            }
//        }
//        mapView
//    }, modifier = Modifier.fillMaxSize())
//}
@Composable
fun MapboxUserRoute(
    destinationLat: Double,
    destinationLon: Double,
    updateIntervalMs: Long = 3_000L // update every 10 seconds
){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Log.d("MapboxUserRoute", "Destination: ($destinationLat, $destinationLon)")
    MapboxOptions.accessToken = BuildConfig.MAPBOX_ACCESS_TOKEN

    AndroidView(factory = { ctx ->
        val mapView = MapView(ctx)
        val mapboxMap = mapView.mapboxMap
        val destinationPoint = Point.fromLngLat(destinationLon, destinationLat)

        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) { style ->

            // Destination marker
            context.getDrawable(R.drawable.ic_location_on_map)?.toBitmap()?.let {
                style.addImage("destination_marker", it)
            }
            style.addSource(geoJsonSource("destination-source") { geometry(destinationPoint) })
            style.addLayer(symbolLayer("destination-layer", "destination-source") {
                iconImage("destination_marker")
                iconAllowOverlap(true)
                iconIgnorePlacement(true)
            })

            // Origin marker
            context.getDrawable(R.drawable.ic_radio_button_checked)?.toBitmap()?.let {
                style.addImage("origin_marker", it)
            }
            style.addSource(geoJsonSource("origin-source") { geometry(Point.fromLngLat(0.0, 0.0)) })
            style.addLayer(symbolLayer("origin-layer", "origin-source") {
                iconImage("origin_marker")
                iconAllowOverlap(true)
                iconIgnorePlacement(true)
            })

            // Route line
            style.addSource(geoJsonSource("route-source") {
                geometry(LineString.fromLngLats(listOf(destinationPoint, destinationPoint)))
            })
            style.addLayer(
                lineLayer("route-layer", "route-source") {
                    lineColor("#1E90FF")
                    lineWidth(8.0)
                    lineCap(LineCap.ROUND)
                    lineJoin(LineJoin.ROUND)
                }
            )

            // Location plugin
            val locationPlugin = mapView.location
            locationPlugin.updateSettings {
                enabled = true
                pulsingEnabled = true
                locationPuck = LocationPuck2D()
            }

            // 🔹 Store latest user location here
            var latestUserPoint: Point? = null

            // Listen for user position updates
            locationPlugin.addOnIndicatorPositionChangedListener { point ->
                latestUserPoint = point
                style.getSourceAs<com.mapbox.maps.extension.style.sources.generated.GeoJsonSource>("origin-source")
                    ?.geometry(point)
            }

            // 🔁 Update route every X seconds
            coroutineScope.launch(Dispatchers.Main) {
                while (true) {
                    latestUserPoint?.let { userPoint ->
                        coroutineScope.launch(Dispatchers.IO) {
                            val routeLine = fetchRoute(userPoint, destinationPoint)
                            routeLine?.let { line ->
                                coroutineScope.launch(Dispatchers.Main) {
                                    style.getSourceAs<com.mapbox.maps.extension.style.sources.generated.GeoJsonSource>("route-source")
                                        ?.geometry(line)

                                    val cameraOptions = CameraOptions.Builder()
                                        .center(userPoint)
                                        .zoom(14.0)
                                        .build()

                                    mapboxMap.easeTo(
                                        cameraOptions,
                                        MapAnimationOptions.Builder().duration(1000).build()
                                    )
                                }
                            }
                        }
                    }
                    delay(updateIntervalMs) // wait before next update
                }
            }
        }
        mapView
    }, modifier = Modifier.fillMaxSize())
}


private fun distanceBetween(p1: Point, p2: Point): Double {
    val dx = p1.longitude() - p2.longitude()
    val dy = p1.latitude() - p2.latitude()
    return hypot(dx * 111320, dy * 110540)
}

fun fetchRoute(origin: Point, destination: Point): LineString? {
    val accessToken = BuildConfig.MAPBOX_ACCESS_TOKEN
    val url =
        "https://api.mapbox.com/directions/v5/mapbox/driving/${origin.longitude()},${origin.latitude()};${destination.longitude()},${destination.latitude()}?geometries=geojson&access_token=$accessToken"

    return try {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        val body = response.body?.string() ?: return null
        val json = JSONObject(body)
        val coords = json.getJSONArray("routes").getJSONObject(0)
            .getJSONObject("geometry").getJSONArray("coordinates")

        val points = mutableListOf<Point>()
        for (i in 0 until coords.length()) {
            val c = coords.getJSONArray(i)
            points.add(Point.fromLngLat(c.getDouble(0), c.getDouble(1)))
        }
        LineString.fromLngLats(points)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Composable
fun MapboxUserLocationBox(
    destinationLat: Double = 0.0,
    destinationLon: Double = 0.0
) {
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(RequestPermission()) { granted ->
        permissionGranted = granted
        if (!granted) {
            Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED

        if (!granted) {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            permissionGranted = true
        }
    }

    if (permissionGranted) {
        MapboxUserRoute(destinationLat, destinationLon)
    }
}
@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context, onResult: (Double, Double) -> Unit){
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                onResult(location.latitude, location.longitude)
            } else {
                // If last location is null, request new one
                val request = LocationRequest.Builder(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    2000L
                ).build()

                fusedLocationClient.requestLocationUpdates(
                    request,
                    object : LocationCallback() {
                        override fun onLocationResult(result: LocationResult) {
                            val loc = result.lastLocation
                            if (loc != null) {
                                onResult(loc.latitude, loc.longitude)
                                fusedLocationClient.removeLocationUpdates(this)
                            }
                        }
                    },
                    Looper.getMainLooper()
                )
            }
        }
}
