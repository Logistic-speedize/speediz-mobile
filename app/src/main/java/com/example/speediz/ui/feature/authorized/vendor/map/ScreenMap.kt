package com.example.speediz.ui.feature.authorized.vendor.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.speediz.BuildConfig
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

@Composable
fun ScreenMap( modifier: Modifier) {
//    AndroidView(
//        factory = { context ->
//            MapView(context).apply {
//                mapboxMap.loadStyle(Style.MAPBOX_STREETS) {
//                    style ->
//                    val annotationApi = annotations
//                    val pointManager = annotationApi.createPointAnnotationManager()
//
//                    //Start and end points
//                    val start = Point.fromLngLat(104.9007, 11.5732)
//                    val end = Point.fromLngLat(104.9236, 11.5536)
//
//                    //marker
//                    pointManager.create(PointAnnotationOptions().withPoint(start).withTextField("Start"))
//                    pointManager.create(PointAnnotationOptions().withPoint(end).withTextField("End"))
//
//                    val centerLng = (start.longitude() + end.longitude()) / 2
//                    val centerLat = (start.latitude() + end.latitude()) / 2
//                    mapboxMap.setCamera(
//                        CameraOptions.Builder()
//                            .center(Point.fromLngLat(centerLng, centerLat))
//                            .zoom(12.0)
//                            .build()
//                    )
//
//                    CoroutineScope(Dispatchers.IO).launch {
//                        val route = getRouteFromMapbox(start, end)
//                        if ( route != null ){
//                            launch (Dispatchers.Main) {
//                                //Draw a stragiht line route
//                                style.addSource(
//                                    geoJsonSource("route-source") {
//                                        geometry(
//                                            route
//                                        )
//                                    }
//                                )
//                                style.addLayer(
//                                    lineLayer("route-layer", "route-source") {
//                                        lineColor("#009688")
//                                        lineWidth(5.0)
//                                        lineCap(LineCap.ROUND)
//                                        lineJoin(LineJoin.ROUND)
//                                    }
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        },
//        modifier = modifier.fillMaxSize()
//    )
}
fun getRouteFromMapbox(start: Point, end: Point): LineString? {
    val accessToken = BuildConfig.MAPBOX_ACCESS_TOKEN
    val url =
        "https://api.mapbox.com/directions/v5/mapbox/driving/" +
                "${start.longitude()},${start.latitude()};" +
                "${end.longitude()},${end.latitude()}" +
                "?geometries=geojson&access_token=$accessToken"

    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()
    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) return null
        val json = JSONObject(response.body?.string() ?: return null)
        val routes = json.getJSONArray("routes")
        if (routes.length() == 0) return null
        val geometry = routes.getJSONObject(0).getString("geometry")
        return LineString.fromJson(geometry)
    }
}