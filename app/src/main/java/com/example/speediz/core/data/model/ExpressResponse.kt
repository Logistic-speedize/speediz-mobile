package com.example.speediz.core.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class ExpressResponse(
    val data: Data
){
    data class Data(
        val data: Map<String, List<ExpressItems>>
    ){
        data class ExpressItems(
            val id: Int,
            val number: String = "",
            val name: String = "",
            val slug: String = "",
            val price: String = "",
            val zone: String = "",
            @SerializedName("picked_up_at")
            val pickupAt: String = "",
            @SerializedName("delivered_at")
            val deliveredAt: String? = null,
            @SerializedName("vendor_id")
            val vendorId: Int = 0,
            @SerializedName("customer_id")
            val customerId: Int = 0,
            @SerializedName("location_id")
            val locationId: Int = 0,
            @SerializedName("driver_id")
            val driverId: Int = 0,
            @SerializedName("shipment_id")
            val shipmentId: Int = 0,
            @SerializedName("invoice_id")
            val invoiceId: Int = 0,
            @SerializedName("delivery_tracking_id")
            val deliveryTrackingId: String? = null,
            @SerializedName("branch_id")
            val branchId: Int = 0,
            @SerializedName("package_type_id")
            val packageTypeId: Int = 0,
            @SerializedName("note")
            val note: String = "",
            val status: String = "",
            @SerializedName("created_at")
            val createdAt: String?,
            @SerializedName("updated_at")
            val updatedAt: String?,
            val customer: CustomerInfo?,
            val vendor: VendorInfo?,
            val location: Location?,
        ){
            data class CustomerInfo(
                val id: Int,
                @SerializedName("first_name")
                val firstName: String? = "",
                @SerializedName("last_name")
                val lastName: String? = "",
                val phone: String? = "",
            )
            data class Location (
                val id: Int,
                val location: String?,
                val lat: Double?,
                val lng: Double?,
            )
            data class  VendorInfo(
                @SerializedName("first_name")
                val firstName: String? = "",
                @SerializedName("last_name")
                val lastName: String? = "",
                @SerializedName("contact_number")
                val contactNumber: String? = "",

                )
        }
    }
}
