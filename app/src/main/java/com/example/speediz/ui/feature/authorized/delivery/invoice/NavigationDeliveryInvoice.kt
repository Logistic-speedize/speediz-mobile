package com.example.speediz.ui.feature.authorized.delivery.invoice

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.speediz.ui.navigation.AuthorizedRoute

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.screenDeliveryInvoice(
    onNavigateTo: (String) -> Unit,
    onBackPress: () -> Unit
) {
    composable(AuthorizedRoute.DeliveryRoute.Invoice.route){
        ScreenDeliveryInvoice(
            onNavigateTo = onNavigateTo,
            onBackPress = onBackPress
        )
    }
}

fun NavController.navigateToDeliveryInvoice() {
    this.navigate(AuthorizedRoute.DeliveryRoute.Invoice.route)
}
