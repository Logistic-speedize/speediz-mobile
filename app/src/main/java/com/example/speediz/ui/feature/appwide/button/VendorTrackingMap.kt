package com.example.speediz.ui.feature.appwide.button

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toBitmap
import com.example.speediz.BuildConfig
import com.example.speediz.R
import com.mapbox.common.MapboxOptions
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun VendorDriverRouteMap(
    driverLat: Double,
    driverLon: Double,
    customerLat: Double,
    customerLon: Double,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    MapboxOptions.accessToken = BuildConfig.MAPBOX_ACCESS_TOKEN

    AndroidView(factory = { ctx ->
        val mapView = MapView(ctx)
        val mapboxMap = mapView.mapboxMap

        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) { style ->
            // MARKERS
            context.getDrawable(R.drawable.ic_radio_button_checked)?.toBitmap()?.let {
                style.addImage("driver_marker" , it)
            }
            style.addSource(geoJsonSource("driver-source") {})
            style.addLayer(
                symbolLayer("driver-layer" , "driver-source") {
                    iconImage("driver_marker")
                    iconAllowOverlap(true)
                }
            )

            context.getDrawable(R.drawable.ic_location_on_map)?.toBitmap()?.let {
                style.addImage("customer_marker" , it)
            }
            style.addSource(geoJsonSource("customer-source") {
                geometry(Point.fromLngLat(customerLon , customerLat))
            })
            style.addLayer(
                symbolLayer("customer-layer" , "customer-source") {
                    iconImage("customer_marker")
                    iconAllowOverlap(true)
                }
            )

            // ROUTE SOURCE
            style.addSource(geoJsonSource("route-source"))
            style.addLayer(
                lineLayer("route-layer" , "route-source") {
                    lineWidth(7.0)
                    lineColor("#1E90FF")
                }
            )

            // Load route initial
            coroutineScope.launch {
                while (true) {
                    val line = fetchRoute(
                        Point.fromLngLat(driverLon , driverLat) ,
                        Point.fromLngLat(customerLon , customerLat)
                    )
                    line?.let {
                        withContext(Dispatchers.Main) {
                            style.getSourceAs<com.mapbox.maps.extension.style.sources.generated.GeoJsonSource>(
                                "route-source"
                            )
                                ?.geometry(it)
                        }
                    }
                    delay(2000L)
                }
            }
        }
        mapView
    }, update = { mapView ->
        val style = mapView.mapboxMap.getStyle() ?: return@AndroidView

        // 🟢 LIVE UPDATE DRIVER MARKER
        style.getSourceAs<com.mapbox.maps.extension.style.sources.generated.GeoJsonSource>("driver-source")
            ?.geometry(Point.fromLngLat(driverLon, driverLat))
    }, modifier = Modifier.fillMaxSize())
}

