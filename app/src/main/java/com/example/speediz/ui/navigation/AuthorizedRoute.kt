package com.example.speediz.ui.navigation

sealed class AuthorizedRoute( val route: String, val id : String? = "") {
    data object VendorHome : AuthorizedRoute(route = "vendor/home")
    data object DeliveryHome : AuthorizedRoute(route = "delivery/home")
    data object Profile : AuthorizedRoute(route = "profile")
    data object Settings : AuthorizedRoute(route = "settings")
}
sealed class VendorRoute ( val route : String, val id : String ? = ""){
    data object Home : VendorRoute(route = "home")
    data object Package : VendorRoute(route = "package") {
        const val PARAM_ID = "packageId"
    }
}
