package com.example.speediz.core.data.delivery

import com.google.gson.annotations.SerializedName

data class StatusRequest(
    val id: String,
    val reason: String
)

data class PickUpStatusRequest(
    val id: String,
    val lng: Double,
    val lat: Double
)

data class CompletedStatusRequest(
    val id: String,
)

data class TrackingLocationRequest(
    @SerializedName("package_id")
    val packageId: Int,
    val lng: Double,
    val lat: Double
)

