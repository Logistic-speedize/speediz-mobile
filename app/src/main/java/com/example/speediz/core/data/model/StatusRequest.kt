package com.example.speediz.core.data.model

data class StatusRequest(
    val id: String,
    val reason: String
)

data class PickUpStatusRequest(
    val id: Int,
    val lng: Double,
    val lat: Double
)

data class CompletedStatusRequest(
    val id: String,
)
