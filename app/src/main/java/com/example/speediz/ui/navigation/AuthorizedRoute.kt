package com.example.speediz.ui.navigation

data class Route(
    val route: String, val id: String?
)
val VENDOR_ROUTE = listOf(
    Route( AuthorizedRoute.VendorRoute.Home.route, AuthorizedRoute.VendorRoute.Home.id),
    Route( AuthorizedRoute.VendorRoute.Package.route, AuthorizedRoute.VendorRoute.Package.id),
    Route( AuthorizedRoute.VendorRoute.Map.route, AuthorizedRoute.VendorRoute.Map.id),
    Route( AuthorizedRoute.VendorRoute.Account.route, AuthorizedRoute.VendorRoute.Account.id),
    Route( AuthorizedRoute.VendorRoute.PackageDetail.route, AuthorizedRoute.VendorRoute.PackageDetail.id)
)
val DELIVERY_ROUTE = listOf(
    Route( AuthorizedRoute.DeliveryRoute.Home.route, AuthorizedRoute.DeliveryRoute.Home.id),
    Route( AuthorizedRoute.DeliveryRoute.Package.route, AuthorizedRoute.DeliveryRoute.Package.id),
    Route( AuthorizedRoute.DeliveryRoute.Map.route, AuthorizedRoute.DeliveryRoute.Map.id),
    Route( AuthorizedRoute.DeliveryRoute.Account.route, AuthorizedRoute.DeliveryRoute.Account.id),
    Route( AuthorizedRoute.DeliveryRoute.PackageDetail.route, AuthorizedRoute.DeliveryRoute.PackageDetail.id)
)
sealed class AuthorizedRoute{
    sealed class DeliveryRoute ( val route : String, val id : String ? = "") : AuthorizedRoute(){
        data object Home : DeliveryRoute(route = "home")
        data object Package : DeliveryRoute(route = "package")
        data object PackageDetail : DeliveryRoute(route = "package_detail", id = "{id}")
        data object Map : DeliveryRoute(route = "map")
        data object Account : DeliveryRoute(route = "account")
    }
    sealed class VendorRoute ( val route : String, val id : String ? = "") : AuthorizedRoute(){
        data object Home : VendorRoute(route = "home")
        data object Package : VendorRoute(route = "package")
        data object PackageDetail : VendorRoute(route = "package_detail", id = "{id}")
        data object Map : VendorRoute(route = "map")
        data object Account : VendorRoute(route = "account")
    }

}