package com.example.speediz.core.data.vendor

import com.google.gson.annotations.SerializedName

data class PackageTrackingDetailResponse(
    val data: PackageDetail
){
    data class PackageDetail(
        val id: Int ,
//        val number: String,
//        @SerializedName("name")
//        val customerName: String = "",
//        val price: String = "",
//        val zone: String = "",
//        val status: String = "",
//        val note: String = "",
        @SerializedName("package")
        val packageDetail: PackageDetailInfo ,
        @SerializedName("delivery")
        val deliveryInfo: DeliveryInfo ,
        @SerializedName("destination")
        val destinationInfo: DestinationInfo ,
        @SerializedName("driver_location")
        val driverLocation : DriverLocation ,
        @SerializedName("vendor")
        val vendorInfo: VendorInfo ,
    ){
        data class PackageDetailInfo(
            val id: Int,
            @SerializedName("package_number")
            val packageNumber: String = "",
            @SerializedName("customer_name")
            val customerName: String = "",
            @SerializedName("customer_phone")
            val customerPhone: String = "",
            @SerializedName("location")
            val location: String = "",
            @SerializedName("total_price")
            val totalPrice: String = "",
            val status: String = "",
        )
        data class DeliveryInfo(
            @SerializedName("shipment_date")
            val shipmentDate: String = "",
            @SerializedName("package_status")
            val packageStatus: String = "",
            @SerializedName("driver_name")
            val driverName: String = "",
            @SerializedName("driver_phone")
            val driverPhone: String = "",
            @SerializedName("delivery_fee")
            val deliveryFee: String = "",
            @SerializedName("driver_type")
            val driverType: String = "",
            )

        data class VendorInfo(
            @SerializedName("vendor_name")
            val vendorName: String = "",
            @SerializedName("pickup_date")
            val pickupDate: String = "",
            @SerializedName("vendor_phone")
            val vendorPhone: String = "",
        )
        data class DestinationInfo(
            val location: String = "",
            val latitude: Double = 0.0,
            val longitude: Double = 0.0,
        )
        data class DriverLocation(
            val lat: Double = 0.0,
            val lng: Double = 0.0,
        )
    }
}