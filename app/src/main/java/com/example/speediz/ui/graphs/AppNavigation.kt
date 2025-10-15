package com.example.speediz.ui.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.speediz.MainViewModel
import com.example.speediz.ui.feature.unauthorized.signIn.SignInViewModel
import com.example.speediz.ui.navigation.AuthorizedRoute
import com.example.speediz.ui.navigation.UnauthorizedRoute
import com.example.speediz.ui.navigation.deliveryAuthorizedNavigate
import com.example.speediz.ui.navigation.unauthorizedNavigate
import com.example.speediz.ui.navigation.vendorAuthorizedNavigate

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isLoggedIn: Boolean,
    role : Int = 3
) {
    val signInViewModel = hiltViewModel<SignInViewModel>()
    val userRole = signInViewModel.role
    val startDestination = if (!isLoggedIn) UnauthorizedRoute.SignIn.route
    else if (userRole == role) AuthorizedRoute.VendorRoute.Home.route
    else AuthorizedRoute.DeliveryRoute.Home.route
    NavHost(
        navController = navController,
        startDestination =  startDestination,
        modifier = modifier,
    ) {
        if (isLoggedIn) {
            if ( userRole == role) {
                 vendorAuthorizedNavigate(
                     navController = navController
                 )
            } else {
                deliveryAuthorizedNavigate(
                    navController = navController
                )
            }
        } else {
            unauthorizedNavigate(
                navController = navController
            )
        }
    }
}