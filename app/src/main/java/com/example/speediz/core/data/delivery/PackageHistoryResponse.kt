package com.example.speediz.core.data.delivery

import com.google.gson.annotations.SerializedName


data class PackageHistoryResponse(
    val data: PackageHistoryData
){
    data class PackageHistoryData(
        @SerializedName("current_page")
        val currentPage: Int,
        @SerializedName("last_page")
        val lastPage: Int,
        @SerializedName("total")
        val totalPage: Int,
        @SerializedName("data")
        val historyItems: List<PackageHistoryItem>,
    ){
        data class PackageHistoryItem(
            val id: Int,
            val number: String,
            val name: String,
            val slug: String,
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

        ){
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
                val lat: Double = 0.0,
                val lng: Double = 0.0,
            )
            data class ShipmentInfo(
                val id: Int,
                @SerializedName("package_id")
                val packageId: Int,
                val number: String = "",
                val type: String = "",
                val status: String = "",
                val description: String = "",
                val date: String = "",
                @SerializedName("delivery_fee")
                val deliveryFee: String = "",
            )
            data class InvoiceInfo(
                val id: Int,
                @SerializedName("vendor_invoice_id")
                val vendorInvoiceId: Int,
                val number: String = "",
                val date : String = "",
                val total: Int = 0,
                val status: String = "",
                val note: String = "",
            )
        }
    }
}