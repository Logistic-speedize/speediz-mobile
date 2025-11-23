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
import com.example.speediz.core.data.delivery.CompletedStatusRequest
import com.example.speediz.core.data.delivery.ExpressDetailResponse
import com.example.speediz.core.data.delivery.PickUpStatusRequest
import com.example.speediz.core.data.delivery.StatusRequest
import com.example.speediz.core.data.delivery.TrackingLocationRequest
import com.example.speediz.ui.feature.appwide.button.DialogDelivery
import com.example.speediz.ui.feature.appwide.button.MapboxUserLocationBox
import com.example.speediz.ui.feature.appwide.button.SPLoading
import com.example.speediz.ui.feature.appwide.button.getCurrentLocation
import com.example.speediz.ui.theme.SPColor

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
                navigateTo = navigateTo,
               viewModel = viewModel
           )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetShowExpressDetail(
    expressDetail: ExpressDetailResponse.ExpressDetailData ?= null,
    currentStatus: String,
    navigateTo: (String) -> Unit,
    viewModel : ExpressDetailViewModel
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
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun ExpressDetail(
    expressDetail: ExpressDetailResponse.ExpressDetailData ?= null,
    currentStatus: String,
    viewModel : ExpressDetailViewModel
) {
    val completedState = viewModel.completedStatus.collectAsState().value
    val cancelState = viewModel.cancelStatus.collectAsState().value
    val rollbackState = viewModel.rollbackStatus.collectAsState().value
    val pickUpState = viewModel.pickUpStatus.collectAsState().value
    val vendorName = expressDetail?.vendor?.firstName + " " + expressDetail?.vendor?.lastName
    val customerName = expressDetail?.customer?.firstName + " " + expressDetail?.customer?.lastName
    val vendorPhone = expressDetail?.vendor?.contactNumber
    val customerPhone = expressDetail?.customer?.contactNumber
    val packageInDollar = expressDetail?.price?.toDoubleOrNull() ?: 0.0
    val packageInRiel = packageInDollar * 4100 // Assuming 1 USD
    val reason = remember { mutableStateOf("") }

    var isCompleted by remember { mutableStateOf(false) }
    var isCancelled by remember { mutableStateOf(false) }
    var isRollback by remember { mutableStateOf(false) }
    var isPickUp by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        getCurrentLocation(context){
            lat, lng ->
            val trackingRequest = TrackingLocationRequest(
                packageId = expressDetail?.id ?: 0,
                lat = lat,
                lng = lng
            )
            viewModel.getTrackingDeliveryLocation(trackingRequest)
        }
    }

    LaunchedEffect(completedState , cancelState , rollbackState , pickUpState) {
        when(completedState) {
            is StatusUiState.Success -> {
                Toast.makeText(
                    context,
                    "Completed Successfully",
                    Toast.LENGTH_LONG
                ).show()
                isCompleted = false
            }
            is StatusUiState.Error -> {
                val message = (completedState).message
                Toast.makeText(
                    context,
                    "Error: $message",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {}
        }
        when (cancelState) {
            is StatusUiState.Success -> {
                Toast.makeText(
                    context,
                    "Cancelled Successfully",
                    Toast.LENGTH_LONG
                ).show()
                Log.d( "ExpressDetail2" , "SUccesss" )
                isCancelled = false
            }
            is StatusUiState.Error -> {
                val message = (cancelState).message
                Log.d( "ExpressDetail2" , "Cancel Error Message: $message" )
                Toast.makeText(
                    context,
                    "Error: $message",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {}
        }
        when (rollbackState) {
            is StatusUiState.Success -> {
                isRollback = false
                Toast.makeText(
                    context ,
                    "Rollback Successfully" ,
                    Toast.LENGTH_LONG
                ).show()
            }

            is StatusUiState.Error -> {
                val message = (rollbackState).message
                Toast.makeText(
                    context ,
                    "Error: $message" ,
                    Toast.LENGTH_LONG
                ).show()
            }

            else -> {}
        }
        when (rollbackState){
            is StatusUiState.Success -> {
                Toast.makeText(
                    context,
                    "Rollback Successfully",
                    Toast.LENGTH_LONG
                ).show()
            }
            is StatusUiState.Error -> {
                val message = (rollbackState).message
                Toast.makeText(
                    context,
                    "Error: $message",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {}
        }

        when (pickUpState){
            is StatusUiState.Success -> {
                isPickUp = false
                Toast.makeText(
                    context,
                    "Pick Up Successfully",
                    Toast.LENGTH_LONG
                ).show()
            }
            is StatusUiState.Error -> {
                val message = (pickUpState).message
                Toast.makeText(
                    context,
                    "Error: $message",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {}
        }

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
                Text( "#SP${expressDetail?.id}" , fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(expressDetail?.location?.location.toString(), fontSize = 15.sp, color = Color.Gray)
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
        Box{
            // Buttons
           when (currentStatus){
               "In_transit" -> {
                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.SpaceBetween
                   ) {
                       Button(
                           onClick = {
                               isCancelled = true
                           } ,
                           colors = ButtonDefaults.buttonColors(containerColor = SPColor.blueInfo) ,
                           shape = RoundedCornerShape(12.dp) ,
                           modifier = Modifier
                               .weight(1f)
                               .height(52.dp)
                       ) {
                           Text("Cancel" , color = Color.White , fontWeight = FontWeight.Bold)
                       }

                       Spacer(modifier = Modifier.width(16.dp))

                       Button(
                           onClick = {
                               isCompleted = true
                           } ,
                           colors = ButtonDefaults.buttonColors(
                               containerColor = MaterialTheme.colorScheme.primary
                           ) ,
                           shape = RoundedCornerShape(12.dp) ,
                           modifier = Modifier
                               .weight(1f)
                               .height(52.dp)
                       ) {
                           Text(
                               "Complete" ,
                               color = Color.White ,
                               fontWeight = FontWeight.Bold
                           )
                       }
                   }
               }
               "Completed" , "Cancelled" -> {
                   Button(
                       onClick = {
                            isRollback = true
                       },
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(50.dp),
                       shape = RoundedCornerShape(12.dp)
                   ) {
                       Text( "Rollback", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                   }
               }
               else -> {
                   Button(
                       onClick = {
                           isPickUp = true
                       },
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(50.dp),
                       shape = RoundedCornerShape(12.dp),
                       colors = ButtonDefaults.buttonColors(
                           containerColor = Color.Red
                       )
                   ) {
                       Text( "Pick up", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                   }
               }
           }
        }
    }
    if ( isPickUp){
        DialogDelivery(
            title = "Pick Up Delivery",
            description = "Are you sure you want to pick up this delivery?",
            onDismissRequest = {
                isPickUp = false
            },
            onConfirm = { it ->
                getCurrentLocation(context) { lat, lng ->
                    val request = PickUpStatusRequest(
                        id = expressDetail?.id ?: 0,
                        lng = lng,
                        lat = lat
                    )
                    viewModel.getPickUpStatus(request)
                }
                Log.d("ExpressDetail2" , "Pick Up Confirmed for ID: ${expressDetail?.id}" )
            },
            isEnablePassValue = false
        )
    }
    if ( isCompleted){
        DialogDelivery(
            title = "Complete Delivery",
            description = "Are you sure you want to complete this delivery?",
            onDismissRequest = {
                isCompleted = false
            },
            onConfirm = { it ->
                val request = CompletedStatusRequest(
                    id = expressDetail?.number.toString()

                )
                viewModel.getCompletedStatus(request)
            },
            isEnablePassValue = false
        )
    }

    if (isCancelled){
        DialogDelivery(
            title = "Cancel Delivery",
            description = "Please provide a reason for cancelling this delivery.",
            onDismissRequest = {
                isCancelled = false
            },
            onConfirm = { it ->
                val request = StatusRequest(
                    id = expressDetail?.number.toString(),
                    reason = it
                )
                viewModel.getCancelStatus(request)
                isCancelled = false
            },
           isEnablePassValue = true,
            onChangeValue = {
                reason.value = it
            }
        )
    }
    if (isRollback){
        DialogDelivery(
            title = "Rollback Delivery",
            description = "Please provide a reason for rolling back this delivery.",
            onDismissRequest = {
                isRollback = false
            },
            onConfirm = { it ->
                val request = StatusRequest(
                    id = expressDetail?.number.toString(),
                    reason = it
                )
                viewModel.getRollbackStatus(request)
                isRollback = false
            },
            isEnablePassValue = true,
            onChangeValue = {
                reason.value = it
            }
        )
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


