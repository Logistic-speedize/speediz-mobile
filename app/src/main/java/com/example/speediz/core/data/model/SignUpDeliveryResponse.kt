package com.example.speediz.core.data.model

import kotlinx.serialization.SerialName


data class SignUpDriverResponse(
    val data: DriverData,
    val meta: MessageDelivery
)

data class DriverData(
    val user: User,
){
    data class User(
        val role: Int,
        val email: String,
        @SerialName("account_status")
        val accountStatus: Int,
        val id: Int
    )
}

data class MessageDelivery(
    val message: Message
){
    data class Message(
        val title: String,
        val message : String
    )
}