package com.example.speediz.core.network.services

import android.content.Context
import android.util.Log
import com.example.speediz.BuildConfig
import com.example.speediz.core.data.delivery.CompletedStatusRequest
import com.example.speediz.core.data.delivery.ExpressDetailResponse
import com.example.speediz.core.data.delivery.ExpressResponse
import com.example.speediz.core.data.vendor.PackageTrackingDetailResponse
import com.example.speediz.core.data.vendor.PackageResponse
import com.example.speediz.core.data.delivery.PickUpStatusRequest
import com.example.speediz.core.data.ResponseErrorModel
import com.example.speediz.core.data.SignInRequest
import com.example.speediz.core.data.SignInResponse
import com.example.speediz.core.data.delivery.DeliveryProfileResponse
import com.example.speediz.core.data.delivery.InvoiceDetailResponse
import com.example.speediz.core.data.delivery.InvoiceResponse
import com.example.speediz.core.data.delivery.SignUpDriverResponse
import com.example.speediz.core.data.vendor.SignUpVendorResponse
import com.example.speediz.core.data.delivery.StatusRequest
import com.example.speediz.core.data.delivery.TrackingLocationRequest
import com.example.speediz.core.data.delivery.PackageHistoryDetailResponse
import com.example.speediz.core.data.delivery.PackageHistoryResponse
import com.example.speediz.core.data.vendor.SignUpVendorRequest
import com.example.speediz.core.network.interceptor.NetworkConnectionInterceptor
import com.example.speediz.core.network.interceptor.TokenInterceptor
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

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
        @Part("first_name") firstName: RequestBody ,
        @Part("last_name") lastName: RequestBody ,
        @Part("dob") dob: RequestBody ,
        @Part("gender") gender: RequestBody ,
        @Part("email") email: RequestBody ,
        @Part("password") password: RequestBody ,
        @Part("password_confirmation") passwordConfirm: RequestBody ,
        @Part("contact_number") contactNumber: RequestBody ,
        @Part("driver_type") driverType: RequestBody ,
        @Part("zone") zone: RequestBody ,
        @Part image: MultipartBody.Part? = null

    ): Response<SignUpDriverResponse>
    @GET("api/delivery/express")
    suspend fun deliveryExpress(): Response<ExpressResponse>

    @GET("api/delivery/express/{id}")
    suspend fun deliveryExpressDetail(
        @Path ("id") id: Int,
    ): Response<ExpressDetailResponse>

    @POST("api/delivery/express/delivered")
    suspend fun completedStatus(
        @Body info: CompletedStatusRequest
    ): Response<ResponseErrorModel>

    @POST("api/delivery/express/cancel")
    suspend fun cancelStatus(
        @Body info: StatusRequest
    ): Response<ResponseErrorModel>

    @POST("api/delivery/express/rollback")
    suspend fun rollbackStatus(
        @Body info: StatusRequest
    ): Response<ResponseErrorModel>

    @POST("api/delivery/express/pickup")
    suspend fun pickUpStatus(
        @Body info: PickUpStatusRequest
    ): Response<ResponseErrorModel>

    @POST("api/delivery/tracking")
    suspend fun deliveryTracking(
        @Body info: TrackingLocationRequest
    ): Response<ResponseErrorModel>

    @GET("api/vendor/packages")
    suspend fun packageList(): Response<PackageResponse>

    @GET("api/vendor/packages/map/{id}")
    suspend fun packageTrackingDetail(
        @Path ("id") id: Int,
    ): Response<PackageTrackingDetailResponse>

    @GET("api/delivery/express/history")
    suspend fun packageHistory(): Response<PackageHistoryResponse>

    @GET("api/delivery/express/history/{id}")
    suspend fun packageHistoryDetail(
        @Path ("id") id: Int,
    ): Response<PackageHistoryDetailResponse>

    @GET("api/delivery/invoice/{id}")
    suspend fun invoiceDeliveryDetail(
        @Path ("id") id: Int,
    ): Response<InvoiceDetailResponse>

    @GET("api/delivery/invoice")
    suspend fun invoiceDelivery(): Response<InvoiceResponse>

    @GET("api/vendor/invoice")
    suspend fun invoiceVendor(): Response<com.example.speediz.core.data.vendor.InvoiceResponse>

    @GET("api/delivery/me")
    suspend fun getDeliveryProfile(): Response<DeliveryProfileResponse>


    companion object {
        val baseUrl = BuildConfig.API_BASE_URL
        operator fun invoke(
            context: Context,
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : ApiService {
            val tokenInterceptor = TokenInterceptor(context)
            val okHttpClient = okhttp3.OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(tokenInterceptor).addInterceptor { chain ->
                    val request = chain.request()
                    Log.d("REQUEST-URL" , request.url.toString())
                    chain.proceed(request)
                }
                .build()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}