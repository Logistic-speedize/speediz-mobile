package com.example.speediz.core.data.model

import android.net.Uri
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpDeliveryRequest(
    @SerialName("first_name")
    val firstname: String,
    @SerialName("last_name")
    val lastname: String,
    @SerialName("dob")
    val dob: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("contact_number")
    val contactNumber: String,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("password_confirmation")
    val passwordConfirm: String,
    @SerialName("driver_type")
    val driverType: String,
    @SerialName("zone")
    val zone: String,
    @SerialName("image")
    val nid: Uri?= null
)
