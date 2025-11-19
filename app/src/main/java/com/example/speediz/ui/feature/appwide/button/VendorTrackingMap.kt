package com.example.speediz.ui.feature.appwide.button

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toBitmap
import com.example.speediz.BuildConfig
import com.example.speediz.R
import com.mapbox.common.MapboxOptions
import com.mapbox.common.location.AccuracyLevel
import com.mapbox.common.location.IntervalSettings
import com.mapbox.common.location.LocationProviderRequest
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.lineProgress
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import kotlin.text.toDouble


@Composable
fun VendorDriverRouteMap(
    driverLat: Double,
    driverLon: Double,
    customerLat: Double,
    customerLon: Double
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var routeLine by remember { mutableStateOf<LineString?>(null) }
    var routeProgress by remember { mutableStateOf(0f) }

    // Create Points
    val driverPoint = remember(driverLat, driverLon) { Point.fromLngLat(driverLon, driverLat) }
    val customerPoint = remember(customerLat, customerLon) { Point.fromLngLat(customerLon, customerLat) }

    // Animate route progress
    val infiniteTransition = rememberInfiniteTransition()
    val animatedProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    routeProgress = animatedProgress

    // Fetch route whenever driverPoint or customerPoint changes
    LaunchedEffect(driverPoint, customerPoint) {
        val line = fetchRoute(driverPoint, customerPoint)
        routeLine = line
        Log.d("VendorDriverRouteMap", "Fetched route line: $routeLine")
    }

    AndroidView(
        factory = { ctx ->
            MapboxOptions.accessToken = BuildConfig.MAPBOX_ACCESS_TOKEN
            val mapView = MapView(ctx)
            val mapboxMap = mapView.mapboxMap

            mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) { style ->

                // ----- DRIVER MARKER -----
                context.getDrawable(R.drawable.ic_radio_button_checked)?.toBitmap()?.let {
                    style.addImage("driver_marker", it)
                }
                style.addSource(geoJsonSource("driver-source") { geometry(driverPoint) })
                style.addLayer(symbolLayer("driver-layer", "driver-source") {
                    iconImage("driver_marker")
                    iconAllowOverlap(true)
                    iconIgnorePlacement(true)
                })

                // ----- CUSTOMER MARKER -----
                context.getDrawable(R.drawable.ic_location_on_map)?.toBitmap()?.let {
                    style.addImage("customer_marker", it)
                }
                style.addSource(geoJsonSource("customer-source") { geometry(customerPoint) })
                style.addLayer(symbolLayer("customer-layer", "customer-source") {
                    iconImage("customer_marker")
                    iconAllowOverlap(true)
                    iconIgnorePlacement(true)
                })

                // ----- ROUTE SOURCE & LAYER -----
                style.addSource(geoJsonSource("route-source"))
                style.addLayer(lineLayer("route-layer", "route-source") {
                    lineWidth(7.0)
                    lineColor("#1E90FF")
                    lineCap(LineCap.ROUND)
                    lineJoin(LineJoin.ROUND)
                    lineTrimOffset(listOf(0.0, routeProgress.toDouble()))
                })

                // ----- Camera -----
                val centerLat = (driverLat + customerLat) / 2
                val centerLon = (driverLon + customerLon) / 2
                mapboxMap.setCamera(
                    CameraOptions.Builder()
                        .center(Point.fromLngLat(centerLon, centerLat))
                        .zoom(12.0)
                        .build()
                )

                // Update route source whenever routeLine changes
                    routeLine?.let { line ->
                        style.getSourceAs<com.mapbox.maps.extension.style.sources.generated.GeoJsonSource>("route-source")
                            ?.geometry(line)
                    }
            }

            mapView
        },
        modifier = Modifier.fillMaxSize(),
        update = { view ->
            routeLine?.let { line ->
                view.mapboxMap.getStyle()
                    ?.getSourceAs<com.mapbox.maps.extension.style.sources.generated.GeoJsonSource>("route-source")
                    ?.geometry(line)
            }
        }
    )
}

