package com.example.speediz

import android.app.Application
import com.mapbox.common.MapboxOptions

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        MapboxOptions.accessToken = BuildConfig.MAPBOX_ACCESS_TOKEN
    }
}