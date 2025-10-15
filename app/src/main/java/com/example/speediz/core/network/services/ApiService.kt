package com.example.speediz.core.network.services

import android.content.Context
import com.example.speediz.MainApplication
import com.example.speediz.core.data.model.SignInRequest
import com.example.speediz.core.data.model.SignInResponse
import com.example.speediz.core.network.interceptor.NetworkConnectionInterceptor
import com.example.speediz.core.network.interceptor.TokenInterceptor
import com.example.speediz.ui.navigation.UnauthorizedRoute
import com.mapbox.maps.MAPBOX_LOCALE
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.Properties

interface ApiService {

    companion object {
        operator fun invoke(
            context: Context,
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : ApiService {
            val tokenInterceptor = TokenInterceptor(context)
            val okHttpClient = okhttp3.OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .addInterceptor(networkConnectionInterceptor)
                .build()
            val gson = com.google.gson.GsonBuilder()
                .setLenient()
                .create()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://logistic.laseavyong.com/") // Replace with your API base URL
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }
    }
}