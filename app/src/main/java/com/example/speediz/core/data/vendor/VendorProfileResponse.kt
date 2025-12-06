package com.example.speediz.core.data.vendor

import com.google.gson.annotations.SerializedName


data class VendorProfileResponse(
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
            val accountStatus: Int = 0,
        )
        data class  Driver(
            val id: Int,
            @SerializedName("first_name")
            val firstName: String = "",
            @SerializedName("last_name")
            val lastName: String = "",
            @SerializedName("business_type")
            val businessType: String = "",
            @SerializedName("business_name")
            val businessName: String = "",
            @SerializedName("business_description")
            val businessDescription: String = "",
            @SerializedName("dob")
            val dob: String,
            @SerializedName("gender")
            val gender: String,
            @SerializedName("contact_number")
            val contactNumber: String,
            @SerializedName("image")
            val imageProfile: String ?= null,
            @SerializedName("address")
            val address: String
        )
    }
}
