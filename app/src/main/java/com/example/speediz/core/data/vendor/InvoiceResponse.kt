package com.example.speediz.core.data.vendor

import com.google.gson.annotations.SerializedName

data class InvoiceResponse(
    val data: Data
) {
    data class Data(
        @SerializedName("vendor_invoices")
        val vendorInvoices: VendorInvoices
    ){
        data class VendorInvoices(
            @SerializedName("data")
            val invoiceItem: List<InvoiceItems> ?= null,
            @SerializedName("current_page")
            val currentPage: Int,
            @SerializedName("per_page")
            val perPage: Int,
            @SerializedName("total")
            val total: Int
        ){
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
    }
}