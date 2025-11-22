package com.example.speediz.core.data.model

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    val email: String,
    val password: String,
    @SerializedName("device_token")
    val deviceToken: String = ""
)
