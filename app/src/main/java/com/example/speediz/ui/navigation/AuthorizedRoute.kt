package com.example.speediz.ui.navigation


sealed class AuthorizedRoute{
    sealed class DeliveryRoute ( val route : String) : AuthorizedRoute(){
        data object Home : DeliveryRoute(route = "delivery_home")
        data object Express : DeliveryRoute(route = "delivery_express")
        data object ExpressDetail : DeliveryRoute(route = "express_detail/{id}")
        data object History: DeliveryRoute(route = "history")
        data object HistoryDetail: DeliveryRoute(route = "history/{id}")
        data object Map : DeliveryRoute(route = "map")
        data object Account : DeliveryRoute(route = "account")
        data object Invoice : DeliveryRoute(route = "invoice")
        data object InvoiceDetail : DeliveryRoute(route = "invoice/{id}")
    }
    sealed class VendorRoute ( val route : String) : AuthorizedRoute(){
        data object Home : VendorRoute(route = "vendor_home")

        data object Package : VendorRoute(route = "vendor_package")
        data object PackageDetail : VendorRoute(route = "vendor_package/{id}")
        data object Map : VendorRoute(route = "map")
        data object Account : VendorRoute(route = "account")
        data object Invoice: VendorRoute(route = "vendor_invoice")
        data object PackageTracking : VendorRoute(route = "vendor_package_tracking")
        data object PackageTrackingDetail : VendorRoute(route = "vendor_package_tracking/{id}")

    }

}