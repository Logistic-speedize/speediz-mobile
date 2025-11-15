package com.example.speediz.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.speediz.ui.feature.authorized.delivery.express.detail.navigationExpressDetail
import com.example.speediz.ui.feature.authorized.delivery.express.detail.screenExpressDetail
import com.example.speediz.ui.feature.authorized.delivery.express.screenExpress
import com.example.speediz.ui.feature.authorized.delivery.history.screenHistory
import com.example.speediz.ui.feature.authorized.delivery.screenDeliveryHome
import com.example.speediz.ui.feature.authorized.vendor.map.screenMap
import com.example.speediz.ui.feature.authorized.vendor.invoiceManagement.invoiceList.screenInvoice
import com.example.speediz.ui.feature.authorized.vendor.packageManagement.packageList.screenPackage
import com.example.speediz.ui.feature.authorized.vendor.packageTracking.screenPackageTracking
import com.example.speediz.ui.feature.authorized.vendor.home.screenHomeVendor

fun NavGraphBuilder.deliveryAuthorizedNavigate(
    navController: NavController
) {
    screenDeliveryHome(
        onNavigateTo = {route ->
            navController.navigate(route)
        }
    )
    screenMap()
    screenExpress(
        navigateTo = { expressId ->
            navController.navigationExpressDetail(expressId)
        },
        onBack = {
            navController.popBackStack()
        }
    )
    screenExpressDetail(
        onBack = {
            navController.popBackStack()
        },
        navigateTo = { route ->
            navController.navigate(route)
        }
    )
        screenHistory(
            onNavigateTo = {route ->
                navController.navigate(route)
            }
        )

    //        screenAccount()
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