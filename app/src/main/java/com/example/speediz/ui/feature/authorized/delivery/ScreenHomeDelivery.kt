package com.example.speediz.ui.feature.authorized.delivery

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.speediz.R
import com.example.speediz.core.data.delivery.ExpressResponse
import com.example.speediz.core.data.vendor.PackageResponse
import com.example.speediz.ui.feature.appwide.button.SPLoading
import com.example.speediz.ui.feature.authorized.delivery.account.AccountVM
import com.example.speediz.ui.feature.authorized.delivery.account.DeliveryProfileState
import com.example.speediz.ui.feature.authorized.delivery.express.ExpressViewModel
import com.example.speediz.ui.feature.authorized.vendor.packageTracking.PackageTrackingViewModel
import com.example.speediz.ui.navigation.AuthorizedRoute
import com.example.speediz.ui.theme.SPColor
import com.example.speediz.ui.theme.SpeedizTheme
import com.example.speediz.ui.theme.spacing
import com.example.speediz.ui.utils.dateFormat

@Composable
fun ScreenHomeDelivery(
    onNavigateTo: (String) -> Unit = { }
) {
    val scrollState = rememberScrollState()
    val profileVM = hiltViewModel<AccountVM>()
    val profileState = profileVM.profileUIState.collectAsState()

    val packageVM = hiltViewModel<ExpressViewModel>()
    val packageState = packageVM.expressFilter.collectAsState()

    LaunchedEffect(profileState) {
        profileVM.fetchProfileData()
    }

    LaunchedEffect(packageState) {
        packageVM.fetchExpressData()
    }

    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
            .statusBarsPadding(),
        topBar = {
            when (val state = profileState.value) {
                is DeliveryProfileState.Loading -> {
                    SPLoading()
                }
                is DeliveryProfileState.Success -> {
                    val profileData = state.response
                    Row(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clickable{
                                    onNavigateTo(AuthorizedRoute.DeliveryRoute.Account.route)
                                }
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray.copy( alpha = 0.3f )),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = profileData.driver.imageProfile,
                                contentDescription = "Profile photo",
                                placeholder = painterResource(R.drawable.ic_profile_fallback),
                                error = painterResource(R.drawable.ic_profile_fallback),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                            )
                        }
                        Spacer( modifier = Modifier.padding( MaterialTheme.spacing.small ) )
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Hello,",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = profileData.driver.firstName,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                is DeliveryProfileState.Error -> {
                    // Handle error state if needed
                }
            }
        },

    ){ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .verticalScroll(scrollState)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // ----- Express -----
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_box) ,
                        contentDescription = "Express" ,
                        tint = MaterialTheme.colorScheme.primary ,
                        modifier = Modifier
                            .size(58.dp)
                            .background(
                                Color.LightGray.copy(0.3f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(16.dp)
                            .clickable{
                                onNavigateTo(AuthorizedRoute.DeliveryRoute.Express.route)
                            }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Express",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // ----- History -----
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_analytics),
                        contentDescription = "History",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(58.dp)
                            .background(
                                Color.LightGray.copy(0.3f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(16.dp)
                            .clickable{
                                onNavigateTo(AuthorizedRoute.DeliveryRoute.History.route)
                            }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "History",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // ----- Map -----
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location_on),
                        contentDescription = "Map",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(58.dp)
                            .background(
                                Color.LightGray.copy(0.3f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(16.dp)
                            .clickable{
                                onNavigateTo(AuthorizedRoute.DeliveryRoute.Invoice.route)
                            }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Invoice",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }


            Spacer(modifier = Modifier.height(24.dp))

            // Search Bar (with border)
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clip(RoundedCornerShape(12.dp))
//                    .border(
//                        width = 2.dp,
//                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
//                        shape = RoundedCornerShape(12.dp)
//                    )
//                    .background(MaterialTheme.colorScheme.surface)
//                    .padding(horizontal = 16.dp, vertical = 12.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_search),
//                    contentDescription = "Search",
//                    modifier = Modifier.size(20.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = "Enter company name",
//                    color = MaterialTheme.colorScheme.onSurfaceVariant,
//                    fontSize = 14.sp
//                )
//            }

            Spacer(modifier = Modifier.height(32.dp))

            // Package list header
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "Package list",
//                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_instant_mix),
//                        contentDescription = "Filter",
//                        modifier = Modifier.size(20.dp)
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(
//                        text = "filters",
//                        color = MaterialTheme.colorScheme.onSurfaceVariant,
//                        fontSize = 14.sp
//                    )
//                }
//            }

 //           Spacer(modifier = Modifier.height(16.dp))
            // Package list
//            Column(
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                packageState.value.take(4).forEach { packageInfo ->
//                    val statusColor = when (packageInfo.status.lowercase()) {
//                        "delivered" -> Color(0xFF4CAF50) // Green
//                        "in transit" -> Color(0xFFFFC107) // Amber
//                        "pending" -> Color(0xFFFF5722) // Deep Orange
//                        else -> Color.Gray
//                    }
//                    PackageCardHome(
//                        packageInfo = packageInfo,
//                        statusColor = statusColor
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(32.dp))
            when {
                packageState.value.isEmpty() -> {
                    Text(
                        text = "No packages available.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                else -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        packageState.value.values.take(4).forEach { packageInfo ->
                            packageInfo.forEach { item ->
                                val statusColor = when (item.status.lowercase()) {
                                    "completed" -> SPColor.greenSuccess // Green
                                    "in_transit" -> SPColor.blueInfo // Amber
                                    "pending" -> SPColor.error // Deep Orange
                                    else -> Color.Gray
                                }
                                PackageCardHome(
                                    packageInfo = item,
                                    statusColor = statusColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PackageCardHome(
    packageInfo: ExpressResponse.Data.ExpressItems ,
    statusColor: Color
) {
    val idPrefix = "#SP"
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.95f))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$idPrefix${packageInfo.id}",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(statusColor)
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = packageInfo.status,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                    Text(
                        text = packageInfo.name,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                Spacer( modifier = Modifier.weight(1f))
                    Text(
                        text = "$${packageInfo.price}",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PackagePreview() {
    SpeedizTheme {
        ScreenHomeDelivery()
    }
}