package com.example.speediz.ui.graphs

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import com.example.speediz.MainViewModel
import com.example.speediz.ui.SPAppState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavHost(
     mySPAppState: SPAppState,
    AppViewModel : MainViewModel
) {
    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides this@SharedTransitionLayout,
        ) {
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
