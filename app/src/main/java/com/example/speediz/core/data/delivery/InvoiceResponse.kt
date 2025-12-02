package com.example.speediz.core.data.delivery

import com.google.gson.annotations.SerializedName

data class InvoiceResponse(
    val data: Data
){
    data class Data(
        @SerializedName("current_page")
        val currentPage: Int,
        @SerializedName("data")
        val invoiceItem: List<InvoiceItems>,
    ){
        data class InvoiceItems(
            val id: Int,
            @SerializedName("package_id")
            val packageId: Int,
            @SerializedName("number")
            val invoiceNumber: String,
            @SerializedName("date")
            val invoiceDate: String,
            @SerializedName("status")
            val invoiceStatus: String,
            @SerializedName("created_at")
            val createdAt: String,
        )
    }
}