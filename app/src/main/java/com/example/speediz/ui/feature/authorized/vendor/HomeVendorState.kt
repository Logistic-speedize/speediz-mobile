package com.example.speediz.ui.feature.authorized.vendor

import com.example.speediz.R
import com.example.speediz.ui.navigation.AuthorizedRoute

data class HomeVendorState(
    val email: String = "",
    val profile: Int = R.drawable.img_default_profile,
    val unreadCount: Int = 0,
    val actions: List<VendorAction> = listOf(
        VendorAction(
            label = "Package Management",
            iconRes = R.drawable.img_package_management,
            route = AuthorizedRoute.VendorRoute.Package.route
        ),
        VendorAction(
            label = "Package Tracking",
            iconRes = R.drawable.img_package_tracking,
            route = AuthorizedRoute.VendorRoute.PackageTracking.route
        ),
        VendorAction(
            label = "Invoice Management",
            iconRes = R.drawable.img_invoice_management,
            route = AuthorizedRoute.VendorRoute.Invoice.route
        )
    ),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class VendorAction(
    val label: String,
    val iconRes: Int,
    val route: String
)