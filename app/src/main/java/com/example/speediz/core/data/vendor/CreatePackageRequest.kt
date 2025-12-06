package com.example.speediz.core.data.vendor

import com.google.gson.annotations.SerializedName

data class CreatePackageRequest(
    @SerializedName("price")
    val price: Double,
    @SerializedName("customer_first_name")
    val customerFirstName: String,
    @SerializedName("customer_last_name")
    val customerLastName: String,
    @SerializedName("customer_phone")
    val customerPhone: String,
    @SerializedName("customer_location")
    val customerLocation: String,
    @SerializedName("customer_lat")
    val customerLatitude: Double,
    @SerializedName("customer_lng")
    val customerLongitude: Double,

)
