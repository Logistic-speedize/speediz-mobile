package com.example.speediz.core.network.services

import android.content.Context
import android.os.Build
import com.example.speediz.BuildConfig
import com.example.speediz.core.data.model.SignInRequest
import com.example.speediz.core.data.model.SignInResponse
import com.example.speediz.core.network.interceptor.NetworkConnectionInterceptor
import com.example.speediz.core.network.interceptor.TokenInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/mobile/login")
    suspend fun signIn(
        @Body info : SignInRequest
    ) : Response<SignInResponse>
    companion object {
        val baseUrl = BuildConfig.API_BASE_URL
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
                .baseUrl(baseUrl) // Replace with your API base URL
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }
    }
}