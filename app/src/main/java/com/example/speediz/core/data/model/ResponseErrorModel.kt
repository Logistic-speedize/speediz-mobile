package com.example.speediz.core.data.model

data class ResponseErrorModel(
    val code: Int?,
    val message: String?,
    val errors: List<String>?
)