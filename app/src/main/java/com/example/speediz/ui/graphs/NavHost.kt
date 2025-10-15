package com.example.speediz.ui.graphs

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.speediz.MainViewModel
import com.example.speediz.ui.navigation.deliveryAuthorizedNavigate
import com.example.speediz.ui.navigation.unauthorizedNavigate
import com.example.speediz.ui.navigation.vendorAuthorizedNavigate

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    isLoggedIn: Boolean,
    role: Roles = Roles.DELIVERY,
    mainViewModel: MainViewModel = MainViewModel() // Ideally use viewModel()
) {
    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides this@SharedTransitionLayout,
        ) {
            androidx.navigation.compose.NavHost(
                navController = navController,
                startDestination = if (isLoggedIn) Graph.AUTH_DELIVERY else Graph.UN_AUTH,
                modifier = Modifier,
            ) {
                if (isLoggedIn) {
                    if (role == Roles.VENDOR) {
                        vendorAuthorizedNavigate(
                            navController = navController,
                            mainViewModel = mainViewModel
                        )
                    } else {
                        deliveryAuthorizedNavigate(
                            navController = navController,
                            mainViewModel = mainViewModel
                        )
                    }
                } else {
                    unauthorizedNavigate(
                        navController = navController,
                        mainViewModel = mainViewModel
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }

object Graph {
    const val ROOT = "root_graph"
    const val AUTH_VENDOR = "auth_vendor_graph"
    const val AUTH_DELIVERY = "auth_delivery_graph"
    const val UN_AUTH = "un_auth_graph"
}
