package com.example.speediz.ui.navigation


sealed class AuthorizedRoute{
    sealed class DeliveryRoute ( val route : String) : AuthorizedRoute(){
        data object Home : DeliveryRoute(route = "delivery_home")
        data object Package : DeliveryRoute(route = "delivery_package")
        data object PackageDetail : DeliveryRoute(route = "package_detail/{id}")
        data object Map : DeliveryRoute(route = "map")
        data object Account : DeliveryRoute(route = "account")
    }
    sealed class VendorRoute ( val route : String) : AuthorizedRoute(){
        data object Home : VendorRoute(route = "vendor_home")
        data object Package : VendorRoute(route = "vendor_package")
        data object PackageDetail : VendorRoute(route = "package_detail/{id}")
        data object Map : VendorRoute(route = "map")
        data object Account : VendorRoute(route = "account")
        data object Invoice: VendorRoute(route = "vendor_invoice")
        data object PackageTracking : VendorRoute(route = "vendor_package_tracking")
    }

}