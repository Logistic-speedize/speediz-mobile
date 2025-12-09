package com.example.speediz.core.data.vendor

import com.google.gson.annotations.SerializedName

data class InvoiceDetailResponse(
    @SerializedName("data")
    val invoiceDetail: InvoiceDetail
){
    data class InvoiceDetail(
        @SerializedName("invoice_number")
        val invoiceNumber: String?,
        @SerializedName("vendor_name")
        val vendorName: String,
        @SerializedName("date")
        val invoiceDate: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("package_summary")
        val packageSummary: PackageSummary,
        @SerializedName("packages_summary_total")
        val packagesSummaryTotal: PackageSummaryTotal,
        @SerializedName("packages")
        val packages: List<PackageItem>
    ){
        data class PackageSummary(
            val pending: Int,
            val completed: Int,
            val cancelled: Int,
            @SerializedName("in_transit")
            val inTransit: Int
        )

        data class PackageSummaryTotal(
            @SerializedName("total_packages")
            val totalPackages: Int,
            @SerializedName("total_package_price")
            val totalPackagePrice: Double,
            @SerializedName("total_delivery_fee")
            val totalDeliveryFee: Double,
            @SerializedName("total_remain")
            val totalRemain: Double
        )
        data class PackageItem(
            val id: Int,
            @SerializedName("number")
            val number: String,
            @SerializedName("price")
            val price: String,
            @SerializedName("description")
            val description: String,
            @SerializedName("zone")
            val zone: String,
            @SerializedName("status")
            val status: String,
            @SerializedName("delivered_at")
            val deliveredAt: String?,
            @SerializedName("delivery_fee")
            val deliveryFee: String
        )
    }
}
