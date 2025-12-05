package com.example.speediz.ui.feature.authorized.vendor.packageTracking

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.speediz.R
import com.example.speediz.core.data.vendor.PackageResponse
import com.example.speediz.ui.theme.SPColor

@Composable
fun ScreenPackageTracking(
    onNavigateTo: (String) -> Unit ,
    onBack: () -> Unit
) {
    val viewModel = hiltViewModel<PackageTrackingViewModel>()
    val filteredList= viewModel.packageFilterInMap.collectAsState()
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(Unit) {
        viewModel.fetchPackageListInMap()
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .padding(8.dp),
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onBack() }
                    ,
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                    tint = Color.Black
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Package Tracking",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.Black
                )
            }
        }
    ) {
            padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAF6))
                .padding(padding)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // --- Search Bar with Search Icon ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp , RoundedCornerShape(12.dp))
                    .background(Color.White , RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp , vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search) ,
                        contentDescription = "Search" ,
                        tint = Color.Gray ,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    BasicTextField(
                        value = searchText ,
                        onValueChange = {
                            searchText = it
                            val idText = searchText.text.filter { char -> char.isDigit() }
                            viewModel.searchPackageInMapByNId(
                                id = idText
                            )
                        } ,
                        textStyle = TextStyle(fontSize = 16.sp) ,
                        decorationBox = { innerTextField ->
                            if (searchText.text.isEmpty()) {
                                Text(
                                    "Search package phone number" ,
                                    color = Color.Gray ,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        } ,
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // --- Packages List ---
            LazyColumn(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                if ( filteredList.value.isEmpty()){
                    item {
                        Text(
                            text = "No packages found.",
                            fontWeight = FontWeight.Bold ,
                            fontSize = 18.sp ,
                            color = Color.Black
                        )
                    }
                } else {
                    items(filteredList.value) { item ->
                        PackageCard(
                            item = item,
                            onNavigateTo = {
                                onNavigateTo(item.id.toString())
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun PackageCard(item: PackageResponse.DataPackage.PackageItem, onNavigateTo : () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp , color = MaterialTheme.colorScheme.primary , RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "#SPD${item.id}",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                color = Color.Black
            )
            Box(
                modifier = Modifier
                    .background(
                        when (item.status) {
                            "in_transit" -> SPColor.blueInfo
                            "completed" -> SPColor.greenSuccess
                            "cancelled" -> SPColor.error
                            else -> SPColor.yellowWarning
                        } ,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                val statusText = when (item.status) {
                    "in_transit" -> "In Transit"
                    "completed" -> "Completed"
                    "cancelled" -> "Cancelled"
                    else -> "Pending"
                }
                Text(
                    text = statusText,
                    color = Color.White,
                    fontSize = 14.sp,
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text("Customer Name", fontSize = 13.sp, color = Color.Gray)
                val vendor = item.customerName
                Text(vendor, fontWeight = FontWeight.Medium, color = Color.Black)
            }
            Column(
                horizontalAlignment = Alignment.End,
            ) {
                Text("Phone", fontSize = 13.sp, color = Color.Gray)
                Text(item.customerPhone , fontWeight = FontWeight.Medium, color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column{
                val deliveryName = "${item.driver?.firstName} ${item.driver?.lastName}".ifEmpty { "N/A" }
                Text("Delivery's Name", fontSize = 13.sp, color = Color.Gray)
                Text(deliveryName, fontWeight = FontWeight.Medium, color = Color.Black)
            }
            Column(
                horizontalAlignment = Alignment.End,
            ) {
                Text("Phone", fontSize = 13.sp, color = Color.Gray)
                Text(item.driver?.contactNumber ?: "N/A", fontWeight = FontWeight.Medium, color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column{
                Text("Delivery Type", fontSize = 13.sp, color = Color.Gray)
                Text(item.driver?.driverType ?: "N/A" , fontWeight = FontWeight.Medium, color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Location", color = Color.Gray, fontSize = 14.sp)
            Text(
                text = item.location.ifEmpty { "N/A" },
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onNavigateTo()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC64B)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("VIEW DETAILS", fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}
