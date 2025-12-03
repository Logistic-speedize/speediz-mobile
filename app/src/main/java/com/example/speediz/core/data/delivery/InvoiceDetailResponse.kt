package com.example.speediz.core.data.delivery

import com.google.gson.annotations.SerializedName

data class InvoiceDetailResponse(
    val data: Data
){
    data class Data(
        val id: Int,
        @SerializedName("number")
        val invoiceNumber: String,
        @SerializedName("date")
        val invoiceDate: String,
        @SerializedName("tota")
        val invoiceTotal: Double,
        @SerializedName("status")
        val invoiceStatus: String,
        @SerializedName("total_package_price")
        val totalPackagePrice: Double,
        @SerializedName("delivery_fee")
        val deliveryFee: Double,
        @SerializedName("package_status_counts")
        val packageStatusCounts: PackageStatusCounts,
        @SerializedName("packages")
        val packages: List<InvoicePackage>,
    ){
        data class  PackageStatusCounts(
            val cancelled: Int,
            val pending: Int,
            @SerializedName("in_transit")
            val inTransit: Int,
            val completed: Int,
        )
        data class InvoicePackage(
            val id: Int,
            @SerializedName("package_number")
            val packageNumber: String,
            @SerializedName("customer_name")
            val customerName: String,
            @SerializedName("location")
            val location: String,
            @SerializedName("customer_phone")
            val customerPhone: String,
            @SerializedName("zone")
            val zone: String,
            @SerializedName("vendor")
            val vendor: VendorInfo,
            @SerializedName("driver")
            val driverInfo: DriverInfo?,
            @SerializedName("shipment")
            val shipment: Shipment,
            @SerializedName("customer")
            val customerInfo: CustomerInfo,
        ){
            data class VendorInfo(
                val id: Int,
                @SerializedName("full_name")
                val fullName: String,
                @SerializedName("contact_number")
                val contactNumber: String,
            )
            data class DriverInfo(
                val id: Int,
                @SerializedName("full_name")
                val fullName: String,
                @SerializedName("contact_number")
                val contactNumber: String,
            )
            data class CustomerInfo(
                val id: Int,
                @SerializedName("full_name")
                val fullName: String,
                @SerializedName("phone")
                val contactNumber: String,
            )
            data class Shipment(
                val id: Int,
                @SerializedName("number")
                val shipmentNumber: String,
                @SerializedName("type")
                val shipmentType: String,
                @SerializedName("description")
                val description: String,
                @SerializedName("price")
                val price: Double = 0.0,
                @SerializedName("date")
                val date: String,
                @SerializedName("delivery_fee")
                val deliveryFee: String,
                @SerializedName("status")
                val status: String,
            )
        }
    }
}
