package com.example.speediz.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.speediz.ui.feature.authorized.delivery.screenDeliveryHome
import com.example.speediz.ui.feature.authorized.vendor.invoiceManagement.invoiceList.screenInvoice
import com.example.speediz.ui.feature.authorized.vendor.packageManagement.packageList.ScreenPackage
import com.example.speediz.ui.feature.authorized.vendor.packageManagement.packageList.screenPackage
import com.example.speediz.ui.feature.authorized.vendor.packageTracking.screenPackageTracking
import com.example.speediz.ui.feature.authorized.vendor.screenHomeVendor

fun NavGraphBuilder.deliveryAuthorizedNavigate(
    navController: NavController
) {
           screenDeliveryHome(
               onNavigateTo = {route ->
                     navController.navigate(route)
               }
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
                onNavigateTo = {route ->
                    navController.navigate(route)
                }
            )

    screenPackageTracking(
        onNavigateTo = {route ->
            navController.navigate(route)
        }
    )
            screenPackage(
                onNavigateTo = {route ->
                    navController.navigate(route)
                }
            )

            screenPackageTracking(
                onNavigateTo = {route ->
                    navController.navigate(route)
                }
            )

            screenInvoice(
                onNavigateTo = {route ->
                    navController.navigate(route)
                }
            )
}