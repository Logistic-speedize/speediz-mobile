package com.example.speediz.ui.feature.authorized.delivery.invoice.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.speediz.R
import com.example.speediz.core.data.delivery.InvoiceDetailResponse
import com.example.speediz.ui.feature.appwide.button.SPLoading
import com.example.speediz.ui.utils.dateFormat
import com.mapbox.maps.extension.style.expressions.dsl.generated.format

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenDeliveryInvoiceDetail(
    id: String,
    onBackPress: () -> Unit
) {
    val viewModel = hiltViewModel<DeliveryInvoiceDetailViewModel>()
    val invoiceDetailState = viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = id){
        viewModel.getInvoiceDetailById(id = id)
    }
    when (val state = invoiceDetailState.value){
        is InvoiceDetailUIState.Success -> {
            val invoiceDetail = state.invoiceDetailData
            val completedPackage = invoiceDetail.packageStatusCounts.completed
            val remainPackage = invoiceDetail.packageStatusCounts.pending +
                    invoiceDetail.packageStatusCounts.inTransit +
                    invoiceDetail.packageStatusCounts.cancelled
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Invoice Detail",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    textAlign = TextAlign.Center
                                )
                            )
                        },
                        navigationIcon = {
                            androidx.tv.material3.Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable { onBackPress() }
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        )
                    )
                },
                containerColor = Color.Transparent,
                modifier = Modifier.padding(16.dp).statusBarsPadding()
            ) {
                    innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding),
                ) {
                    PackageStatusSection(
                        id= invoiceDetail.invoiceNumber,
                        packageStatusInfo = invoiceDetail.packageStatusCounts,
                        date = invoiceDetail.invoiceDate
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Package Information",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    invoiceDetail.packages.forEach { item ->
                        PackageCard(packageInfo = item)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    androidx.compose.material3.Text(text = "Total packages completed: ${completedPackage}pcs", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))
                    androidx.compose.material3.Text(text = "Total packages remained: ${remainPackage}pcs", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))
                    androidx.compose.material3.Text(text = "Total package price: ${invoiceDetail.totalPackagePrice}$", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    androidx.compose.material3.Text(text = "Total delivery fee: ${invoiceDetail.deliveryFee}$", fontSize = 16.sp)
                }

            }
        }
        is InvoiceDetailUIState.Loading -> {
            SPLoading()
        }
        else -> {}
    }
}

@Composable
fun PackageStatusSection(
    id: String,
    packageStatusInfo: InvoiceDetailResponse.Data.PackageStatusCounts,
    date: String = "2025-12-01"
){
    val cancelledCounts = packageStatusInfo.cancelled
    val pendingCounts = packageStatusInfo.pending + packageStatusInfo.inTransit
    val completedCounts = packageStatusInfo.completed
    val total = cancelledCounts + pendingCounts + completedCounts + cancelledCounts
    val date = dateFormat(date).format(
        java.util.Locale.getDefault(), "dd-MM-yyyy"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 0.dp)
    ){
        // Display invoice info dynamically using the `id`
        androidx.compose.material3.Text(text = "Invoice No: $id", fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.material3.Text(text = "Date: ${date}", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.material3.Text(text = "Total Packages: ${total}", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.material3.Text(text = "Pending Packages: ${pendingCounts}", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.material3.Text(text = "Completed Packages: ${completedCounts}", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.material3.Text(text = "Cancelled Packages: ${cancelledCounts}", fontSize = 16.sp)
    }
}

@Composable
fun PackageCard(
    packageInfo: InvoiceDetailResponse.Data.InvoicePackage
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, androidx.compose.material3.MaterialTheme.colorScheme.inverseSurface.copy(0.3f), RoundedCornerShape(16.dp))
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Rounded.Info,
                contentDescription = "Package",
                modifier = Modifier.padding(4.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = "Package Invoice" ,
                color = Color.Black ,
                fontSize = 16.sp ,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(Modifier.height(10.dp))

        Row {
            Text(text = "Invoice ID" , color = Color.Gray , fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            Text(
                text = "#${packageInfo.id}",
                color = Color.Black ,
                fontSize = 14.sp ,
                fontWeight = FontWeight.Medium
            )
        }
        Row {
            Text(text = "Date" , color = Color.Gray , fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            Text(
                text = dateFormat(packageInfo.shipment.date).format(
                    packageInfo.shipment.date, "dd-MM-yyyy"
                ),
                color = Color.Black ,
                fontSize = 14.sp ,
                fontWeight = FontWeight.Medium
            )
        }
        Row {
            Text(text = "Package Price" , color = Color.Gray , fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            Text(
                text = "$${packageInfo.shipment.price}",
                color = Color.Black ,
                fontSize = 14.sp ,
                fontWeight = FontWeight.Medium
            )
        }
        Row {
            Text(text = "Delivery Fee" , color = Color.Gray , fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            Text(
                text = "$${packageInfo.shipment.deliveryFee}",
                color = Color.Black ,
                fontSize = 14.sp ,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview (showBackground = true)
@Composable
fun ScreenDeliveryInvoiceDetailPreview() {
    ScreenDeliveryInvoiceDetail(
        id = "INV-001",
        onBackPress = {}
    )
}
