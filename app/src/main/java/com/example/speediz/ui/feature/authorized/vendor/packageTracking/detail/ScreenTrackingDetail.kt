package com.example.speediz.ui.feature.authorized.vendor.packageTracking.detail

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.speediz.R
import com.example.speediz.core.data.model.PackageTrackingDetailResponse
import com.example.speediz.ui.feature.appwide.button.MapboxUserLocationBox
import com.example.speediz.ui.feature.appwide.button.VendorDriverRouteMap
import com.example.speediz.ui.feature.authorized.delivery.express.detail.StatusItem
import com.example.speediz.ui.theme.SPColor
import kotlinx.coroutines.delay

@Composable
fun ScreenTrackingDetail(
    id: String,
    onNavigateTo: (String) -> Unit,
    onBack: () -> Unit
) {
    val viewModel = hiltViewModel<TrackingDetailViewModel>()
    val uiState = viewModel.uiState.collectAsState()
    val packageDetail = viewModel.packageDetail.collectAsState().value
//    LaunchedEffect(id, packageDetail, uiState) {
//        viewModel.getPackageDetail(id)
//    }
    LaunchedEffect(Unit) {
        viewModel.getPackageDetail(id)
        delay(2000L)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize().statusBarsPadding().padding(8.dp),
        containerColor = Color.Transparent,
        topBar = {
            Row(
                modifier = Modifier
                    .background(
                        color = Color.Transparent
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onBack() }
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Express",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    ) {
            padding ->
        Box(modifier = Modifier.padding(padding)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ){
                Log.d(
                    "TrackingDetailViewModel" ,
                    "Package Detail in Screen: $packageDetail"
                )
                    VendorDriverRouteMap(
                        driverLat = packageDetail?.data?.destinationInfo?.latitude ?: 0.0 ,
                        driverLon = packageDetail?.data?.destinationInfo?.longitude ?: 0.0 ,
                        customerLat = packageDetail?.data?.driverLocation?.lat ?: 0.0 ,
                        customerLon = packageDetail?.data?.driverLocation?.lng ?: 0.0,
                    )
               // Log.d("ExpressDetail" , "Lat: ${packageDetail.data.} , Lon: ${expressDetail?.data?.location?.lng}" )
            }
            BottomSheetShowPackageDetail(
                packageDetail = packageDetail,
                currentStatus = packageDetail?.data?.packageDetail?.status.toString(),
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetShowPackageDetail(
    packageDetail: PackageTrackingDetailResponse ?= null,
    currentStatus: String,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val showSheet = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){
        Button(
            onClick = {
                showSheet.value = true },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text( "View Details", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
    if (showSheet.value){
        ModalBottomSheet(
            onDismissRequest = {
                showSheet.value = !showSheet.value
            },
            sheetState = sheetState,
            dragHandle = {}
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                PackageDetail(
                    packageDetail = packageDetail?.data,
                    status = currentStatus
                )
            }
        }
    }
}

@Composable
fun PackageDetail(
    packageDetail: PackageTrackingDetailResponse.PackageDetail ?= null,
    status: String,
) {
    val customerName = packageDetail?.packageDetail?.customerName.toString()
    val deliveryName =  packageDetail?.deliveryInfo?.driverName.toString()
    val customerPhone =  packageDetail?.packageDetail?.customerPhone.toString()
    val deliveryPhone =  packageDetail?.deliveryInfo?.driverPhone.toString()
    val packageInDollar = packageDetail?.packageDetail?.totalPrice?.toDouble()
    val packageInRiel = packageInDollar?.times(410) // Assuming 1 USD
    val reason = remember { mutableStateOf("") }
    val currentStatus = remember {
        mutableStateOf(status)
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
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
            Column {
                Text( "#SP${packageDetail?.id.toString()}" , fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(packageDetail?.destinationInfo?.location.toString(), fontSize = 15.sp, color = Color.Gray)
            }
            Box(
                modifier = Modifier
                    .background(
                        color =when (currentStatus.value) {
                            "in_transit" -> SPColor.blueInfo
                            "completed" -> SPColor.greenSuccess
                            "cancelled" -> Color.Red
                            else -> SPColor.yellowWarning
                        },
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(currentStatus.value , color = Color.White, fontSize = 14.sp)
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
                active = currentStatus.value == "pending"
            )
            StatusItem(
                title = "In_Transit",
                active = currentStatus.value == "in_transit"
            )
            StatusItem(
                title = "Completed",
                active = currentStatus.value == "completed"
            )
            StatusItem(
                title = "Cancelled",
                active = currentStatus.value == "cancelled"
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Sender & Receiver Info
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Customer Name", fontSize = 13.sp, color = Color.Gray)
                Text(customerName, fontWeight = FontWeight.Medium, fontSize = 16.sp)
                Text("Delivery Name", fontSize = 13.sp, color = Color.Gray)
                Text( deliveryName , fontWeight = FontWeight.Medium)
                Text("Delivery Type", fontSize = 13.sp, color = Color.Gray)
                Text( packageDetail?.deliveryInfo?.driverType.toString() , fontWeight = FontWeight.Medium)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("Phone", fontSize = 13.sp, color = Color.Gray)
                Text(customerPhone, fontWeight = FontWeight.Medium, fontSize = 16.sp)
                Text("Delivery’s Number", fontSize = 13.sp, color = Color.Gray)
                Text(deliveryPhone , fontWeight = FontWeight.Medium)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Price Info
        Column {
            Text("Package price(\$):  ${packageInDollar}", fontWeight = FontWeight.Bold)
            Text("Package price(riel):  ${packageInRiel}", fontWeight = FontWeight.Bold)
            Text("Delivery Fee:  \$2.00", fontWeight = FontWeight.Bold)
        }
    }
}