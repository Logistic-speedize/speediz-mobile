package com.example.speediz

import android.app.Application
import com.mapbox.common.module.okhttp.MapboxOkHttpService
import com.mapbox.maps.MapboxMap
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: Application(){
    override fun onCreate() {
        super.onCreate()
      //  MapboxMap.getInstance(this, BuildConfig.MAPBOX_ACCESS_TOKEN)
    }
}