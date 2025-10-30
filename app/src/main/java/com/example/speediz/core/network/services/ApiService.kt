package com.example.speediz.core.network.services

import android.content.Context
import android.os.Build
import com.example.speediz.BuildConfig
import com.example.speediz.core.data.model.SignInRequest
import com.example.speediz.core.data.model.SignInResponse
import com.example.speediz.core.data.model.SignUpDriverResponse
import com.example.speediz.core.data.model.SignUpVendorRequest
import com.example.speediz.core.data.model.SignUpVendorResponse
import com.example.speediz.core.network.interceptor.NetworkConnectionInterceptor
import com.example.speediz.core.network.interceptor.TokenInterceptor
import com.example.speediz.ui.navigation.UnauthorizedRoute
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ApiService {
    @POST("api/mobile/login")
    suspend fun signIn(
        @Body info : SignInRequest
    ) : Response<SignInResponse>

    @POST("api/vendor/register")
    suspend fun vendorSignUp(
        @Body info: SignUpVendorRequest
    ): Response<SignUpVendorResponse>

    @Multipart
    @POST("api/delivery/register")
    suspend fun deliverySignUp(
        @Part("first_name") firstName: RequestBody,
        @Part("last_name") lastName: RequestBody,
        @Part("dob") dob: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("password_confirmation") passwordConfirm: RequestBody,
        @Part("contact_number") contactNumber: RequestBody,
        @Part("driver_type") driverType: RequestBody,
        @Part("zone") zone: RequestBody,
        @Part image: MultipartBody.Part? = null

    ): Response<SignUpDriverResponse>
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