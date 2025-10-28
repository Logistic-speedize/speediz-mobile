package com.example.speediz.ui.feature.unauthorized.chooseToSignUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.speediz.R
import com.example.speediz.ui.navigation.UnauthorizedRoute
import com.example.speediz.ui.theme.FontSize

@Composable
fun ScreenChooseToSignUp(
    onNavigateTo : ( String) -> Unit,
    onBackPress : () -> Unit
) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_on_board),
                contentDescription = "OnBoard Image",
                modifier = Modifier.fillMaxSize()
            )
        }
        Scaffold (
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ){ innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding( innerPadding ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Icon",
                    tint = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp).align(alignment = Alignment.Start).clickable {
                        onBackPress()
                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_choose_to_sign_up),
                    contentDescription = "OnBoard Image",
                    modifier = Modifier.fillMaxWidth()
                        .height(400.dp)
                )
                Text(
                    text = "Welcome to Speediz!" ,
                    modifier = Modifier.padding(4.dp).align(alignment = Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = FontSize.large,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Choose your account type to get started." ,
                    modifier = Modifier.padding(4.dp).align(alignment = Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = FontSize.medium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer( modifier = Modifier.padding(48.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp)
                            .padding(horizontal = 16.dp)
                            .background(color = Color.DarkGray.copy(alpha = 0.1f) , shape = MaterialTheme.shapes.medium)
                        ,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                        )
                        ,
                        onClick = {
                            onNavigateTo( UnauthorizedRoute.VendorSignUp.route)
                        },
                        content = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_vendor),
                                    contentDescription = "Sign In Image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .align(alignment = Alignment.CenterHorizontally)
                                )
                                Text(
                                    text = "Vendor",
                                    fontSize = FontSize.small,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            }
                        }
                    )
                    Spacer( modifier = Modifier.padding(24.dp))
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp)
                            .padding(horizontal = 16.dp)
                            .background(color = Color.DarkGray.copy(alpha = 0.1f) , shape = MaterialTheme.shapes.medium)
                        ,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                        )
                        ,
                        onClick = {
                            onNavigateTo(UnauthorizedRoute.DeliverySignUp.route)
                        },
                        content = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_delivery),
                                    contentDescription = "Sign In Image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .align(alignment = Alignment.CenterHorizontally)
                                )
                                Text(
                                    text = "Delivery",
                                    fontSize = FontSize.small,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            }
                        }
                    )
                }
            }
        }
}