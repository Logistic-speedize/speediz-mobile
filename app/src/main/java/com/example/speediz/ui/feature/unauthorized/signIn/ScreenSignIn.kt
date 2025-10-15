package com.example.speediz.ui.feature.unauthorized.signIn

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.speediz.R
import com.example.speediz.core.data.model.SignInRequest
import com.example.speediz.ui.feature.appwide.button.Button
import com.example.speediz.ui.navigation.AuthorizedRoute

@Composable
fun ScreenSignIn(
    onNavigateTo: (String) -> Unit
) {
    val viewModel = hiltViewModel<SignInViewModel>()
    val signInState = viewModel.signInState.collectAsState().value
    val context = LocalContext.current
    LaunchedEffect(signInState) {
        when (signInState) {
            is SignInState.Success -> {
                Toast.makeText(context, "Sign in successful", Toast.LENGTH_SHORT).show()
            }
            is SignInState.DeliveryRoute -> onNavigateTo(AuthorizedRoute.DeliveryRoute.Home.route)
            is SignInState.VendorRoute -> onNavigateTo(AuthorizedRoute.VendorRoute.Home.route)
            else -> {
                Toast.makeText(context, "Sign in failed or unknown state", Toast.LENGTH_SHORT).show()
                SignInState.Error(
                    (signInState as? SignInState.Error)?.message ?: "Unknown error occurred"
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
            innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo) ,
                contentDescription = "Sign In Image" ,
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )
            Text(
                text = "Sign In",
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer( modifier = Modifier.padding(8.dp) )
            OutlinedTextField(
                value = viewModel.email ,
                onValueChange = {
                   viewModel.onEmailChanged( it)
                    viewModel.email = it
                } ,
                label = { Text("Email", color = Color.DarkGray) } ,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer( modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                value = viewModel.password ,
                onValueChange = {
                    viewModel.onPasswordChanged(it)
                    viewModel.password = it
                },
                label = { Text("Password", color = Color.DarkGray) },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer( modifier = Modifier.padding(8.dp))
            Text(
                text = "Forgot Password?" ,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize ,
                fontWeight = FontWeight.Medium ,
                modifier = Modifier
                    .align(alignment = Alignment.End) ,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer( modifier = Modifier.padding(24.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp)
                    .background(color = MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.medium)
                ,
                onClick = {
                    onSignIn( viewModel, SignInRequest(viewModel.email, viewModel.password))
                },
                title = "Sign In"
            )
            Spacer( modifier = Modifier.padding(24.dp))
            Text(
                text = "Don't have an account? Sign Up" ,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize ,
                fontWeight = FontWeight.Medium ,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally) ,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

fun onSignIn(
    viewModel: SignInViewModel ,
    signInRequest: SignInRequest
) {
    viewModel.signIn(signInRequest)
}
