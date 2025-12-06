package com.example.speediz.ui.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.speediz.ui.feature.authorized.delivery.account.screenAccount
import com.example.speediz.ui.feature.authorized.delivery.express.detail.navigationExpressDetail
import com.example.speediz.ui.feature.authorized.delivery.express.detail.screenExpressDetail
import com.example.speediz.ui.feature.authorized.delivery.express.screenExpress
import com.example.speediz.ui.feature.authorized.delivery.history.detail.navigateToHistoryDetail
import com.example.speediz.ui.feature.authorized.delivery.history.detail.screenHistoryDetail
import com.example.speediz.ui.feature.authorized.delivery.history.screenHistory
import com.example.speediz.ui.feature.authorized.delivery.invoice.detail.navigateToDeliveryInvoiceDetail
import com.example.speediz.ui.feature.authorized.delivery.invoice.detail.screenDeliveryInvoiceDetail
import com.example.speediz.ui.feature.authorized.delivery.invoice.screenDeliveryInvoice
import com.example.speediz.ui.feature.authorized.delivery.screenDeliveryHome
import com.example.speediz.ui.feature.authorized.vendor.account.navigationVendorAccount
import com.example.speediz.ui.feature.authorized.vendor.account.screenVendorAccount
import com.example.speediz.ui.feature.authorized.vendor.map.screenMap
import com.example.speediz.ui.feature.authorized.vendor.invoiceManagement.invoiceList.screenInvoice
import com.example.speediz.ui.feature.authorized.vendor.packageManagement.packageList.screenPackage
import com.example.speediz.ui.feature.authorized.vendor.packageTracking.screenPackageTracking
import com.example.speediz.ui.feature.authorized.vendor.home.screenHomeVendor
import com.example.speediz.ui.feature.authorized.vendor.invoiceManagement.detail.navigationInvoiceDetail
import com.example.speediz.ui.feature.authorized.vendor.invoiceManagement.detail.screenInvoiceDetail
import com.example.speediz.ui.feature.authorized.vendor.packageManagement.create.navigateToCreatePackage
import com.example.speediz.ui.feature.authorized.vendor.packageManagement.create.screenCreatePackage
import com.example.speediz.ui.feature.authorized.vendor.packageManagement.detail.navigationPackageDetail
import com.example.speediz.ui.feature.authorized.vendor.packageManagement.detail.screenPackageDetail
import com.example.speediz.ui.feature.authorized.vendor.packageTracking.detail.navigationTrackingDetail
import com.example.speediz.ui.feature.authorized.vendor.packageTracking.detail.screenTrackingDetail

@RequiresApi(Build.VERSION_CODES.O)
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
            navController.navigateToDeliveryInvoiceDetail(route)
        },
        onBackPress = {
            navController.popBackStack()
        }
    )
    screenAccount(
        onLogOut = {
            navController.navigate(UnauthorizedRoute.SignIn.route) {
                popUpTo(0) { inclusive = true }   // clears the entire backstack
                launchSingleTop = true
            }
        },
        onBack = {
            navController.popBackStack()
        },
    )
    screenDeliveryInvoiceDetail (
        onBackPress = {
            navController.popBackStack()
        }
    )
    //        screenAccount()
}
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.vendorAuthorizedNavigate(
    navController: NavController
) {
    screenHomeVendor(
        onNavigateTo = {route ->
            navController.navigate(route)
        },
        onNavigateToProfile = {
            navController.navigationVendorAccount()
            Log.d( "NavigationVendorAccount", "${ navController.currentDestination?.route }" )
        }
    )
    screenPackage(
        onNavigateTo = {id ->
            navController.navigationPackageDetail(id)
        },
        onBack = {
            navController.popBackStack()
        },
        onNavigateToCreatePackage = {
            navController.navigateToCreatePackage()
        }
    )
    screenPackageDetail(
        onBack = {
            navController.popBackStack()
        }
    )
    screenCreatePackage(
        onBack = {
            navController.popBackStack()
        },
        onCreate = {
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
    screenVendorAccount(
        onLogOut = {
            navController.navigate(UnauthorizedRoute.SignIn.route) {
                popUpTo(0) { inclusive = true }   // clears the entire backstack
                launchSingleTop = true
            }
        },
        onBack = {
            navController.popBackStack()
        },
    )
}