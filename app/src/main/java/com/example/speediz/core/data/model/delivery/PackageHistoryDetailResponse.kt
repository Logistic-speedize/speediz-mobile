package com.example.speediz.core.data.model.delivery

import com.google.gson.annotations.SerializedName

data class PackageHistoryDetailResponse(
    val data: PackageHistoryDetailData
) {
    data class PackageHistoryDetailData(
        val id: Int,
        val number: String = "",
        val name: String = "",
        val slug: String = "",
        val price: String = "",
        val description: String = "",
        val zone: String = "",
        @SerializedName("picked_up_at")
        val pickedUpAt: String = "",
        @SerializedName("delivered_at")
        val deliveredAt: String = "",
        val status: String = "",
        @SerializedName("vendor")
        val vendorInfo: VendorInfo,
        @SerializedName("customer")
        val customerInfo: CustomerInfo,
        @SerializedName("location")
        val locationInfo: LocationInfo,
        @SerializedName("shipment")
        val shipmentInfo: ShipmentInfo,
        @SerializedName("invoice")
        val invoiceInfo: InvoiceInfo,
    ) {
        data class VendorInfo(
            val id: Int,
            @SerializedName("first_name")
            val firstName: String = "",
            @SerializedName("last_name")
            val lastName: String = "",
            @SerializedName("contact_number")
            val contactNumber: String = ""
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

        data class LocationInfo(
            val id: Int,
            val location: String = "",
        )

        data class DeliveryInfo(
            val id: Int,
            @SerializedName("first_name")
            val firstName: String = "",
            @SerializedName("last_name")
            val lastName: String = "",
            @SerializedName("driver_type")
            val driverType: String = "",
            @SerializedName("contact_number")
            val contactNumber: String = ""
        )

        data class ShipmentInfo(
            val id: Int,
            @SerializedName("package_id")
            val packageId: Int,
            val number: String = "",
            val type: String = "",
            val description: String = "",
            val date: String = "",
            @SerializedName("delivery_fee")
            val deliveryFee: String = "",
            val status : String = ""
        )

        data class InvoiceInfo(
            val id: Int,
            val number: String = "",
            val date : String = "",
            val total: Int = 0,
            val status: String = "",
            val note: String = "",
        )
    }
}
