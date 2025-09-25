package com.example.model.response

data class AccountResponse(
    val accountNo: String,
    val accountName: String,
    val balance: Double,
    val currency: String
)
