package com.example.speediz.ui.navigation

sealed class AuthorizedRoute( val route: String, val id : String? = "") {
    data object VendorHome : AuthorizedRoute(route = "vendor/home")
    data object DeliveryHome : AuthorizedRoute(route = "delivery/home")
    data object Profile : AuthorizedRoute(route = "profile")
    data object Settings : AuthorizedRoute(route = "settings")
}
sealed class DeliveryRoute ( val route : String, val id : String ? = ""){
    data object Home : DeliveryRoute(route = "home")
    data object Package : DeliveryRoute(route = "package")
    data object PackageDetail : DeliveryRoute(route = "package_detail", id = "{id}")
    data object Map : DeliveryRoute(route = "map")
    data object Account : DeliveryRoute(route = "account")
}
sealed class VendorRoute ( val route : String, val id : String ? = ""){
    data object Home : VendorRoute(route = "home")
    data object Package : VendorRoute(route = "package")
    data object PackageDetail : VendorRoute(route = "package_detail", id = "{id}")
    data object Map : VendorRoute(route = "map")
    data object Account : VendorRoute(route = "account")
}
