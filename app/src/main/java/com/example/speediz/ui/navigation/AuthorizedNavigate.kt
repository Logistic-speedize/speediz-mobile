package com.example.speediz.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.speediz.ui.feature.authorized.delivery.express.detail.navigationExpressDetail
import com.example.speediz.ui.feature.authorized.delivery.express.detail.screenExpressDetail
import com.example.speediz.ui.feature.authorized.delivery.express.screenExpress
import com.example.speediz.ui.feature.authorized.delivery.history.detail.navigateToHistoryDetail
import com.example.speediz.ui.feature.authorized.delivery.history.detail.screenHistoryDetail
import com.example.speediz.ui.feature.authorized.delivery.history.screenHistory
import com.example.speediz.ui.feature.authorized.delivery.invoice.screenDeliveryInvoice
import com.example.speediz.ui.feature.authorized.delivery.screenDeliveryHome
import com.example.speediz.ui.feature.authorized.vendor.map.screenMap
import com.example.speediz.ui.feature.authorized.vendor.invoiceManagement.invoiceList.screenInvoice
import com.example.speediz.ui.feature.authorized.vendor.packageManagement.packageList.screenPackage
import com.example.speediz.ui.feature.authorized.vendor.packageTracking.screenPackageTracking
import com.example.speediz.ui.feature.authorized.vendor.home.screenHomeVendor
import com.example.speediz.ui.feature.authorized.vendor.invoiceManagement.detail.navigationInvoiceDetail
import com.example.speediz.ui.feature.authorized.vendor.invoiceManagement.detail.screenInvoiceDetail
import com.example.speediz.ui.feature.authorized.vendor.packageManagement.detail.navigationPackageDetail
import com.example.speediz.ui.feature.authorized.vendor.packageManagement.detail.screenPackageDetail
import com.example.speediz.ui.feature.authorized.vendor.packageTracking.detail.navigationTrackingDetail
import com.example.speediz.ui.feature.authorized.vendor.packageTracking.detail.screenTrackingDetail

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
            onNavigateTo = {id ->
                navController.navigateToHistoryDetail(id)
            },
            onBack = {
                navController.popBackStack()
            }
        )
    screenHistoryDetail (
        onBack = {
            navController.popBackStack()
        }
    )
    screenDeliveryInvoice(
        onNavigateTo = {route ->
            navController.navigate(route)
        },
        onBackPress = {
            navController.popBackStack()
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
    screenPackage(
        onNavigateTo = {id ->
            navController.navigationPackageDetail(id)
        },
        onBack = {
            navController.popBackStack()
        }
    )
    screenPackageDetail(
        onBack = {
            navController.popBackStack()
        }
    )
    screenPackageTracking(
        onNavigateTo = { id ->
            navController.navigationTrackingDetail(id)
        },
        onBack = {
            navController.popBackStack()
        }
    )
    screenTrackingDetail(
        onBack = {
            navController.popBackStack()
        },
        onNavigateTo = { route ->
            navController.navigate(route)
        }
    )
    screenInvoice(
        onNavigateTo = { id ->
            navController.navigationInvoiceDetail(id)
        },
        onBack = {
            navController.popBackStack()
        }
    )
    screenInvoiceDetail(
        onBack = { navController.popBackStack() }
    )
}