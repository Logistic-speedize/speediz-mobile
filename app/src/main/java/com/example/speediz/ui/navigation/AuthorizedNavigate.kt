package com.example.speediz.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.speediz.ui.feature.unauthorized.signIn.SignInViewModel
import com.example.speediz.ui.feature.authorized.delivery.Package.screenPackage
import com.example.speediz.ui.feature.authorized.delivery.ScreenHomeDelivery
import com.example.speediz.ui.feature.authorized.delivery.account.screenAccount
import com.example.speediz.ui.feature.authorized.delivery.history.screenHistory
import com.example.speediz.ui.feature.authorized.delivery.map.screenMap
import com.example.speediz.ui.feature.authorized.delivery.screenDeliveryHome
import com.example.speediz.ui.feature.authorized.vendor.ScreenHomeVendor
import com.example.speediz.ui.feature.authorized.vendor.home.screenHome
import com.example.speediz.ui.feature.authorized.vendor.screenHomeVendor
import com.example.speediz.ui.graphs.Graph

fun NavGraphBuilder.deliveryAuthorizedNavigate(
    navController: NavController
) {
           screenDeliveryHome(
               onNavigateTo = {}
           )
//        screenMap()
//        screenPackage()
//        screenAccount()
//        screenHistory()
}
fun NavGraphBuilder.vendorAuthorizedNavigate(
    navController: NavController
) {
            screenHomeVendor(
                onNavigateTo = {}
            )
//        screenHomeVendor(
//            onNavigateTo = {
//                route ->
//                AuthorizedRoute.VendorRoute.Home.route
//            }
//        )
//    }
}