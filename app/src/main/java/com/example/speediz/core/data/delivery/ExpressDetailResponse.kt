package com.example.speediz.core.data.delivery

import com.google.gson.annotations.SerializedName

data class ExpressDetailResponse(
    val data : ExpressDetailData
){
    class ExpressDetailData(
        val id: Int,
        val number: String,
        val name: String,
        val slug: String,
        val price: String,
        val description: String,
        val status: String,
        @SerializedName("created_at")
        val createdAt: String,
        val vendor: Vendor,
        val delivery: Delivery,
        val customer: Customer,
        val location: Location
    ){
        data class Vendor(
            val id: Int,
            @SerializedName("first_name")
            val firstName: String,
            @SerializedName("last_name")
            val lastName: String,
            @SerializedName("contact_number")
            val contactNumber: String
        )
        data class Delivery(
            val id: Int,
            @SerializedName("first_name")
            val firstName: String ?= "",
            @SerializedName("last_name")
            val lastName: String ?= "",
            @SerializedName("contact_number")
            val contactNumber: String ?= ""
        )
        data class Customer(
            val id: Int,
            @SerializedName("first_name")
            val firstName: String ?= "",
            @SerializedName("last_name")
            val lastName: String ? = "",
            @SerializedName("phone")
            val contactNumber: String
        )
       data  class Location(
            val id: Int,
            val location: String,
            val lat: Double,
            val lng: Double,
        )
    }
}