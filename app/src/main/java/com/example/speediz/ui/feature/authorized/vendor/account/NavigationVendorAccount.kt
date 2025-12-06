package com.example.speediz.ui.feature.authorized.vendor.account

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.speediz.ui.navigation.AuthorizedRoute

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.screenVendorAccount(
    onLogOut: () -> Unit,
    onBack: () -> Unit,
) {
    composable(AuthorizedRoute.VendorRoute.Account.route) {
        ScreenProfileScreen(
            onLogOut = onLogOut,
            onBackPress = onBack,
        )
    }
}
fun NavController.navigationVendorAccount(
   navOptions: NavOptions? = null
    
) {
    this.navigate(AuthorizedRoute.VendorRoute.Account.route, navOptions)
}