package com.example.speediz.core.data

data class ResponseErrorModel(
    val code: Int?,
    val message: String?,
    val errors: String?,
)