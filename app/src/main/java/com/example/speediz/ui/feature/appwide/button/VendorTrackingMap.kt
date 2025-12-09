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
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.getSourceAs


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

    val vendorPoint = remember(driverLat, driverLon) { Point.fromLngLat(driverLon, driverLat) }
    val customerPoint = remember(customerLat, customerLon) { Point.fromLngLat(customerLon, customerLat) }

    // Animate route progress
    val infiniteTransition = rememberInfiniteTransition()
    val animatedProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    routeProgress = animatedProgress
    MapboxOptions.accessToken = BuildConfig.MAPBOX_ACCESS_TOKEN

    // Fetch route once
    LaunchedEffect(vendorPoint, customerPoint) {
        val line = fetchRoute(vendorPoint, customerPoint)
        routeLine = line
    }

    AndroidView(factory = { ctx ->
        val mapView = MapView(ctx)
        val mapboxMap = mapView.mapboxMap

        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) { style ->

            // Vendor marker
            context.getDrawable(R.drawable.ic_radio_button_checked)?.toBitmap()?.let {
                style.addImage("vendor_marker", it)
            }
            style.addSource(geoJsonSource("vendor-source") { geometry(vendorPoint) })
            style.addLayer(symbolLayer("vendor-layer", "vendor-source") {
                iconImage("vendor_marker")
                iconAllowOverlap(true)
                iconIgnorePlacement(true)
            })

            // Customer marker
            context.getDrawable(R.drawable.ic_location_on_map)?.toBitmap()?.let {
                style.addImage("customer_marker", it)
            }
            style.addSource(geoJsonSource("customer-source") { geometry(customerPoint) })
            style.addLayer(symbolLayer("customer-layer", "customer-source") {
                iconImage("customer_marker")
                iconAllowOverlap(true)
                iconIgnorePlacement(true)
            })

            // Route layer
            style.addSource(geoJsonSource("route-source"))
            style.addLayer(lineLayer("route-layer", "route-source") {
                lineColor("#1E90FF")
                lineWidth(7.0)
                lineCap(LineCap.ROUND)
                lineJoin(LineJoin.ROUND)
                lineTrimOffset(listOf(0.0, routeProgress.toDouble()))
            })

            // Camera
            val centerLat = (driverLat + customerLat) / 2
            val centerLon = (driverLon + customerLon) / 2
            mapboxMap.setCamera(
                CameraOptions.Builder()
                    .center(Point.fromLngLat(centerLon, centerLat))
                    .zoom(12.0)
                    .build()
            )
        }

        mapView
    }, modifier = Modifier.fillMaxSize(),
        update = { view ->
            val style = view.mapboxMap.getStyle() ?: return@AndroidView

            // Update route
            routeLine?.let { line ->
                style.getSourceAs<com.mapbox.maps.extension.style.sources.generated.GeoJsonSource>("route-source")
                    ?.geometry(line)
            }
        }
    )
}


