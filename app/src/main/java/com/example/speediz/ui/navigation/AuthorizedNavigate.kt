package com.example.speediz.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.speediz.ui.feature.authorized.delivery.express.detail.navigationExpressDetail
import com.example.speediz.ui.feature.authorized.delivery.express.detail.screenExpressDetail
import com.example.speediz.ui.feature.authorized.delivery.express.screenExpress
import com.example.speediz.ui.feature.authorized.delivery.history.screenHistory
import com.example.speediz.ui.feature.authorized.delivery.screenDeliveryHome
import com.example.speediz.ui.feature.authorized.vendor.map.screenMap
import com.example.speediz.ui.feature.authorized.vendor.screenHomeVendor

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
}