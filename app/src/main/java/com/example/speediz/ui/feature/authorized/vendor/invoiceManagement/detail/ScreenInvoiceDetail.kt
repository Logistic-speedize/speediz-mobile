package com.example.speediz.ui.feature.authorized.vendor.invoiceManagement.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.speediz.R
import com.example.speediz.core.data.delivery.InvoiceDetailResponse
import com.example.speediz.core.data.vendor.InvoiceResponse
import com.example.speediz.ui.feature.appwide.button.SPLoading
import com.example.speediz.ui.feature.authorized.delivery.invoice.detail.DeliveryInvoiceDetailViewModel
import com.example.speediz.ui.feature.authorized.delivery.invoice.detail.InvoiceDetailUIState
import com.example.speediz.ui.theme.SPColor
import com.example.speediz.ui.utils.dateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenInvoiceDetail(
    id: String,
    onBack: () -> Unit
) {
    val viewModel = hiltViewModel<VendorInvoiceDetailVM>()
    val invoiceDetailState = viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = id){
        Log.d( "ScreenInvoiceDetail", "ScreenInvoiceDetail: $id" )
        viewModel.getInvoiceDetailById(id = id)
    }
    when (val state = invoiceDetailState.value){
        is VendorInvoiceDetailUIState.Success -> {
            val invoiceDetail = state.invoiceDetailData
            val completedPackage = invoiceDetail.packageSummary.completed
            val remainPackage = invoiceDetail.packageSummary.pending +
                    invoiceDetail.packageSummary.inTransit +
                    invoiceDetail.packageSummary.cancelled
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            androidx.tv.material3.Text(
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
                                    .clickable { onBack() }
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
                        id= invoiceDetail.invoiceNumber.toString(),
                        packageStatusInfo = invoiceDetail.packageSummary,
                        date = invoiceDetail.invoiceDate
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    androidx.tv.material3.Text(
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
                    androidx.compose.material3.Text(text = "Total package price: ${invoiceDetail.packagesSummaryTotal.totalPackagePrice}$", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    androidx.compose.material3.Text(text = "Total delivery fee: ${invoiceDetail.packagesSummaryTotal.totalDeliveryFee}$", fontSize = 16.sp)
                }

            }
        }
        is VendorInvoiceDetailUIState.Loading -> {
            SPLoading()
        }
        else -> {}
    }
}

@Composable
fun PackageStatusSection(
    id: String,
    packageStatusInfo: com.example.speediz.core.data.vendor.InvoiceDetailResponse.InvoiceDetail.PackageSummary,
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
    packageInfo: com.example.speediz.core.data.vendor.InvoiceDetailResponse.InvoiceDetail.PackageItem
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
            androidx.tv.material3.Text(
                text = "Package Invoice",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(Modifier.height(10.dp))

        Row {
            androidx.tv.material3.Text(text = "Invoice ID", color = Color.Gray, fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            androidx.tv.material3.Text(
                text = "#${packageInfo.id}",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Row {
            androidx.tv.material3.Text(text = "Date", color = Color.Gray, fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            androidx.tv.material3.Text(
                text = dateFormat(packageInfo.deliveredAt.toString()).format(
                    packageInfo.deliveredAt, "dd-MM-yyyy"
                ),
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Row {
            androidx.tv.material3.Text(text = "Package Price", color = Color.Gray, fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            androidx.tv.material3.Text(
                text = "$${packageInfo.price}",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Row {
            androidx.tv.material3.Text(text = "Delivery Fee", color = Color.Gray, fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            androidx.tv.material3.Text(
                text = "$${packageInfo.deliveredAt}",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Row {
            androidx.tv.material3.Text(text = "Status", color = Color.Gray, fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            androidx.tv.material3.Text(
                text = "$${packageInfo.status}",
                color = when(packageInfo.status){
                    "completed" -> SPColor.greenSuccess
                    "pending" -> SPColor.blueInfo
                    else -> SPColor.error
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
