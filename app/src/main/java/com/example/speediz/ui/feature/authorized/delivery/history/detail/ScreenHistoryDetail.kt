package com.example.speediz.ui.feature.authorized.delivery.history.detail

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.speediz.R
import com.example.speediz.core.data.delivery.PackageHistoryDetailResponse
import com.example.speediz.ui.theme.SPColor
import com.example.speediz.ui.utils.convertDateCalendar
import com.example.speediz.ui.utils.dateFormat

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHistoryDetail(
    onBack: () -> Unit,
    id: String
) {
    val viewModel = hiltViewModel<HistoryDetailViewModel>()
    val historyDetail by viewModel.historyDetail.collectAsState()
    LaunchedEffect(id) {
        viewModel.getHistoryDetailById(id)
        Log.d( "ScreenHistoryDetail" , "Fetching history detail for ID: $id" )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "History Detail",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    androidx.compose.material3.IconButton(
                        onClick = { onBack() }
                    ) {
                       Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(
                    color = MaterialTheme.colorScheme.background
                )
                .fillMaxWidth()
        ) {
            HistoryDetail(
                expressDetail = historyDetail,

            )
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryDetail(
    expressDetail: PackageHistoryDetailResponse.PackageHistoryDetailData?= null,
) {
    val vendorName = expressDetail?.vendorInfo?.firstName + " " + expressDetail?.vendorInfo?.lastName
    val customerName = expressDetail?.customerInfo?.firstName + " " + expressDetail?.customerInfo?.lastName
    val vendorPhone = expressDetail?.vendorInfo?.contactNumber
    val customerPhone = expressDetail?.customerInfo?.contactNumber
    val packageInDollar = expressDetail?.price?.toDoubleOrNull() ?: 0.0
    val packageInRiel = packageInDollar * 4100 // Assuming 1 USD
    val currentStatus = when (expressDetail?.status) {
        "in_transit" -> "In_transit"
        "completed" -> "Completed"
        "cancelled" -> "Cancelled"
        else -> "Pending"
    }

    val pickUpAt = convertDateCalendar(
        dateFormat(expressDetail?.pickedUpAt.toString()),
        "yyyy-MM-dd HH:mm:ss",
        "dd-mm-yyyy"
    )
    val deliveredAt = convertDateCalendar(
        dateFormat(expressDetail?.deliveredAt.toString()),
        "yyyy-MM-dd HH:mm:ss",
        "dd-mm-yyyy"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
            )
            .padding(20.dp)
    ) {
        // Package Info
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text( "#SP${expressDetail?.id}" , fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(expressDetail?.locationInfo?.location.toString(), fontSize = 15.sp, color = Color.Gray)
            }
            Box(
                modifier = Modifier
                    .background(
                        color =when (currentStatus) {
                            "In_transit" -> SPColor.blueInfo
                            "Completed" -> SPColor.greenSuccess
                            "Cancelled" -> Color.Red
                            else -> SPColor.yellowWarning
                        },
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(currentStatus, color = Color.White, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(
                "Package Timeline",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Pickup At
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Picked Up At", color = Color.Gray, fontSize = 14.sp)
                Text(
                    pickUpAt,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Arrived At
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Arrived At", color = Color.Gray, fontSize = 14.sp)
                Text(
                    deliveredAt,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        // Timeline
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusItem(
                title = "Pending",
                active = currentStatus == "Pending"
            )
            StatusItem(
                title = "In_Transit",
                active = currentStatus == "In_transit"
            )
            StatusItem(
                title = "Completed",
                active = currentStatus == "Completed"
            )
            StatusItem(
                title = "Cancelled",
                active = currentStatus == "Cancelled"
            )
        }
        Spacer(modifier = Modifier.height(28.dp))
        // Sender & Receiver Info
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("From", fontSize = 13.sp, color = Color.Gray)
                Text(vendorName, fontWeight = FontWeight.Medium, fontSize = 16.sp)
                Text("Sender’s Number", fontSize = 13.sp, color = Color.Gray)
                Text( vendorPhone.toString() , fontWeight = FontWeight.Medium)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("To", fontSize = 13.sp, color = Color.Gray)
                Text(customerName, fontWeight = FontWeight.Medium, fontSize = 16.sp)
                Text("Receiver’s Number", fontSize = 13.sp, color = Color.Gray)
                Text(customerPhone.toString() , fontWeight = FontWeight.Medium)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        // Price Info
        Column {
            Text("Package price(\$):  ${packageInDollar}", fontWeight = FontWeight.Bold)
            Text("Package price(riel):  ${packageInRiel}", fontWeight = FontWeight.Bold)
            Text("Delivery Fee:  \$2.00", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(28.dp))
    }
}
@Composable
fun StatusItem(title: String, active: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = R.drawable.ic_radio_button_checked),
            contentDescription = title,
            tint = if (active) MaterialTheme.colorScheme.primary else Color.Gray,
            modifier = Modifier.size(28.dp)
        )
        Text(
            text = title,
            fontSize = 12.sp,
            color = if (active) MaterialTheme.colorScheme.primary else Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}
