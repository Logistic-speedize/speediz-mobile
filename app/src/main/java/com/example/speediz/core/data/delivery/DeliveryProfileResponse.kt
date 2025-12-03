package com.example.speediz.core.data.delivery

import com.google.gson.annotations.SerializedName

data class DeliveryProfileResponse(
    val data: Data
){
    data class Data(
        val user: User,
        val driver: Driver
    ){
        data class  User(
            val id: Int ,
            val role: Int = 4,
            val email: String,
            @SerializedName("account_status")
            val accountStatus: String = "",
        )
        data class  Driver(
            val id: Int,
            @SerializedName("first_name")
            val firstName: String = "",
            @SerializedName("last_name")
            val lastName: String = "",
            @SerializedName("driver_type")
            val driverType: String = "",
            @SerializedName("driver_description")
            val driverDescription: String = "",
            @SerializedName("dob")
            val dob: String,
            @SerializedName("gender")
            val gender: String,
            @SerializedName("zone")
            val zone: String,
            @SerializedName("contact_number")
            val contactNumber: String,
            @SerializedName("telegram_contact")
            val telegramContact: String,
            @SerializedName("image")
            val imageProfile: String ?= null,
            @SerializedName("address")
            val address: String
        )
    }
}
