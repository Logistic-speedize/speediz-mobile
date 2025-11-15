package com.example.speediz.ui.feature.authorized.vendor.invoiceManagement.invoiceList

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.speediz.ui.navigation.AuthorizedRoute

fun NavGraphBuilder.screenInvoice(
    onNavigateTo : (String) -> Unit
) {
    composable(AuthorizedRoute.VendorRoute.Invoice.route) {
        ScreenInvoice(
            onNavigateTo = onNavigateTo
        )
    }
}

fun NavController.navigateToInvoiceVendor() {
    this.navigate(AuthorizedRoute.VendorRoute.Invoice.route)
}