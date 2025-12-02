package com.example.speediz.core.data.vendor

import com.google.gson.annotations.SerializedName

data class InvoiceResponse(
    val data: Data
) {
    data class Data(
        @SerializedName("data")
        val invoiceItem: List<InvoiceItems>
    )

    data class InvoiceItems(
        val id: Int,
        @SerializedName("invoice_number")
        val invoiceNumber: String?,
        @SerializedName("date")
        val invoiceDate: String,
        @SerializedName("status")
        val status: String
    )
}