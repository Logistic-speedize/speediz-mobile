package com.example.speediz.ui.graphs

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.speediz.MainViewModel
import com.example.speediz.ui.feature.unauthorized.signIn.SignInViewModel
import com.example.speediz.ui.navigation.AuthorizedRoute
import com.example.speediz.ui.navigation.UnauthorizedRoute
import com.example.speediz.ui.navigation.deliveryAuthorizedNavigate
import com.example.speediz.ui.navigation.unauthorizedNavigate
import com.example.speediz.ui.navigation.vendorAuthorizedNavigate
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val signInViewModel = hiltViewModel<SignInViewModel>()
    val isLoggedIn = signInViewModel.isLoggedIn.collectAsState().value
    val userRole = signInViewModel.role.collectAsState().value
    val startDestination = if (!isLoggedIn) {
        UnauthorizedRoute.SignIn.route
    }else {
        if (userRole == 3) AuthorizedRoute.VendorRoute.Home.route
        else AuthorizedRoute.DeliveryRoute.Home.route
    }
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