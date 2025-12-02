package com.example.speediz.ui.feature.authorized.vendor.invoiceManagement.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.speediz.ui.feature.authorized.vendor.packageManagement.detail.ScreenPackageDetail
import com.example.speediz.ui.navigation.AuthorizedRoute

fun NavGraphBuilder.screenInvoiceDetail(
    onBack: () -> Unit
) {
    val route = AuthorizedRoute.VendorRoute.InvoiceDetail.route
    val argumentName = listOf(
        navArgument("id") {
            type = NavType.StringType
        }
    )
    composable(
        route = route,
        arguments = argumentName
    ) { backStackEntry ->
        val packageId = backStackEntry.arguments?.getString("id") ?: ""
        ScreenInvoiceDetail(
            id = packageId,
            onBack = onBack
        )
    }
}

fun NavController.navigationInvoiceDetail(
    invoiceId: String
){
    this.navigate(
        AuthorizedRoute.VendorRoute.InvoiceDetail.route.replace("{id}", invoiceId)
    )
}