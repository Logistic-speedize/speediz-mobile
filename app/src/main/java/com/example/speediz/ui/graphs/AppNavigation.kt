package com.example.speediz.ui.graphs

import android.util.Log
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.speediz.ui.feature.unauthorized.signIn.SignInViewModel
import com.example.speediz.ui.navigation.AuthorizedRoute
import com.example.speediz.ui.navigation.UnauthorizedRoute
import com.example.speediz.ui.navigation.deliveryAuthorizedNavigate
import com.example.speediz.ui.navigation.unauthorizedNavigate
import com.example.speediz.ui.navigation.vendorAuthorizedNavigate

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavigation(
    navController : NavHostController ,
    modifier : Modifier = Modifier ,
    role : Int? ,
) {
    val signInViewModel = hiltViewModel<SignInViewModel>()
    val isLoggedIn = signInViewModel.isLoggedIn.collectAsState().value
    val userRole = role
    val startDestination = if (!isLoggedIn) {
        UnauthorizedRoute.SignIn.route
    }else {
        if (userRole == 3) AuthorizedRoute.VendorRoute.Home.route
        else AuthorizedRoute.DeliveryRoute.Home.route
    }
    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides this@SharedTransitionLayout,
        )
        {
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = modifier,
            ) {
                Log.d("AppNavigation", "isLogedIn: $isLoggedIn")
                Log.d("AppNavigation", "User role: $userRole")
                unauthorizedNavigate(
                    navController = navController
                )
                vendorAuthorizedNavigate(
                    navController = navController
                )
                deliveryAuthorizedNavigate(
                    navController = navController
                )

            }
        }
    }
}
@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }
