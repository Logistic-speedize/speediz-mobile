package com.example.speediz.ui.feature.authorized.delivery.invoice.detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.speediz.ui.navigation.AuthorizedRoute

fun NavGraphBuilder.screenDeliveryInvoiceDetail(
    onBackPress: () -> Unit
) {
   val route = AuthorizedRoute.DeliveryRoute.InvoiceDetail.route
    val argument = listOf(
        navArgument("id") {
            type = androidx.navigation.NavType.StringType
        }
    )
    composable(
        route = route,
        arguments = argument
    ) { backStackEntry ->
        val invoiceId = backStackEntry.arguments?.getString("id") ?: ""
        ScreenDeliveryInvoiceDetail(
            id = invoiceId,
            onBackPress = onBackPress
        )
    }
}

fun NavController.navigateToDeliveryInvoiceDetail(invoiceId: String) {
    this.navigate(
        AuthorizedRoute.DeliveryRoute.InvoiceDetail.route.replace("{id}", invoiceId)
    )
}
