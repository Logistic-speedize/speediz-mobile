package com.example.speediz.core.data.vendor

import com.google.gson.annotations.SerializedName

data class PackageResponse(
    val data: DataPackage
){
    data class DataPackage(
        val packages: List<PackageItem>,
        val total: Int,
        @SerializedName("per_page")
        val perPage: Int,
        @SerializedName("current_page")
        val currentPage: Int,
        @SerializedName("last_page")
        val lastPage: Int,
        val from: Int,
        val to: Int,
    ){
        data class PackageItem(
            val id: Int,
            @SerializedName("package_number")
            val packageNumber: String = "",
            @SerializedName("customer_name")
            val customerName: String = "",
            @SerializedName("customer_phone")
            val customerPhone: String = "",
            @SerializedName("location")
            val location: String = "",
            @SerializedName("date")
            val date: String = "",
            @SerializedName("status")
            val status: String = "",
            val driver: DriverInfo ?= null,
            val customer: CustomerInfo,
        ){
            data class DriverInfo(
                val id: Int,
                @SerializedName("first_name")
                val firstName: String,
                @SerializedName("last_name")
                val lastName: String,
                @SerializedName("contact_number")
                val contactNumber: String = "",
                @SerializedName("driver_type")
                val driverType: String = "",
            )

            data class CustomerInfo(
                val id: Int,
                @SerializedName("first_name")
                val firstName: String = "",
                @SerializedName("last_name")
                val lastName: String = "",
                @SerializedName("phone")
                val contactNumber: String = ""
            )

        }
    }
}