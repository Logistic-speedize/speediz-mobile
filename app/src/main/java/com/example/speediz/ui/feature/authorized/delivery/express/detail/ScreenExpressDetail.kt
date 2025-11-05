package com.example.speediz.ui.feature.authorized.delivery.express.detail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.speediz.R
import com.example.speediz.core.data.model.ExpressDetailResponse
import com.example.speediz.ui.feature.appwide.button.MapboxUserLocationBox
import com.example.speediz.ui.feature.appwide.button.SPLoading

@Composable
fun ScreenExpressDetail(
    id: String,
    onBack: () -> Unit,
    navigateTo: (String) -> Unit
) {
    val viewModel = hiltViewModel<ExpressDetailViewModel>()
    val uiState = viewModel.uiState.collectAsState().value
    val expressDetail = viewModel.expressDetail.collectAsState().value
    var currentStatus by remember { mutableStateOf("") }
    val currentLat = expressDetail?.data?.location?.lat ?: 0.0
    val currentLon = expressDetail?.data?.location?.lng ?: 0.0
    LaunchedEffect(id, expressDetail, uiState) {
        viewModel.getExpressDetail(id.toInt())
    }
    when ( uiState){
        is ExpressDetailUiState.Loading -> {
            SPLoading()
        }
        is ExpressDetailUiState.Success -> {
            val expressDetail = uiState.detail
            currentStatus =
                when (expressDetail.data.status) {
                    "pending" -> "Pending"
                    "in_transit" -> "In_transit"
                    "completed" -> "Completed"
                    "cancelled" -> "Cancelled"
                    else -> ""
                }
        }
        is ExpressDetailUiState.Error -> {
           val message = uiState.message
            Toast.makeText(
                LocalContext.current,
                "Error: $message",
                Toast.LENGTH_LONG
            ).show()
        }
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
               if ( currentLat != 0.0 && currentLon != 0.0){
                   MapboxUserLocationBox(
                       destinationLat = currentLat,
                       destinationLon = currentLon
                   )
               }
                Log.d("ExpressDetail" , "Lat: ${expressDetail?.data?.location?.lat} , Lon: ${expressDetail?.data?.location?.lng}" )
            }
            BottomSheetShowExpressDetail(
               expressDetail = expressDetail?.data,
                currentStatus = currentStatus,
                navigateTo = navigateTo
           )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetShowExpressDetail(
    expressDetail: ExpressDetailResponse.ExpressDetailData ?= null,
    currentStatus: String,
    navigateTo: (String) -> Unit
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
                ExpressDetail(
                    expressDetail = expressDetail,
                    currentStatus = currentStatus,
                    navigateTo = navigateTo
                )
            }
        }
    }
}

@Composable
fun ExpressDetail(
    expressDetail: ExpressDetailResponse.ExpressDetailData ?= null,
    currentStatus: String,
    navigateTo: (String) -> Unit
) {
    val vendorName = expressDetail?.vendor?.firstName + " " + expressDetail?.vendor?.lastName
    val customerName = expressDetail?.customer?.firstName + " " + expressDetail?.customer?.lastName
    val vendorPhone = expressDetail?.vendor?.contactNumber
    val customerPhone = expressDetail?.customer?.contactNumber
    val packageInDollar = expressDetail?.price?.toDoubleOrNull() ?: 0.0
    val packageInRiel = packageInDollar * 4100 // Assuming 1 USD
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
                Text( "#SP${expressDetail?.id}" , fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(expressDetail?.location?.location.toString(), fontSize = 15.sp, color = Color.Gray)
            }
            Box(
                modifier = Modifier
                    .background(
                        Color(0xFF4AA8F8),
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(currentStatus , color = Color.White, fontSize = 14.sp)
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

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /* Cancel */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
            ) {
                Text("Cancel", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { /* Start shipping */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
            ) {
                Text("Start Shipping", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
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


