package com.example.speediz.ui.feature.authorized.delivery.invoice.detail

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import coil.decode.ImageSource
import com.example.speediz.R
import com.example.speediz.core.data.delivery.InvoiceDetailResponse
import com.example.speediz.ui.utils.dateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenDeliveryInvoiceDetail(
    id: String,
    onBackPress: () -> Unit
) {
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
                modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) {
        innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
        ) {
            PackageStatusSection(id= "1")
            androidx.compose.material3.Text(text = "Total packages: 3pcs", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            androidx.compose.material3.Text(text = "Total package price: 70$", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            androidx.compose.material3.Text(text = "Total delivery fee: 6$", fontSize = 16.sp)
        }
    }
}

@Composable
fun PackageStatusSection(
    id: String,
    packageStatusInfo: Map<String, Int> = mapOf(
        "Pending" to 2,
        "Completed" to 5,
        "Cancelled" to 1
    )
){
    val cancelledCounts = "CANCELLED"
    val pendingCounts = "PENDING"
    val completedCounts = "COMPLETED"
    val date = "2025-12-01"
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
            Image(
                painter = painterResource(id = R.drawable.ic_box),
                contentDescription = "Package Icon",
                modifier = Modifier.size(24.dp).border(
                    width = 1.dp,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(12.dp)
                )

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
                text = packageInfo.id.toString(),
                color = Color.Black ,
                fontSize = 14.sp ,
                fontWeight = FontWeight.Medium
            )
        }
        Row {
            Text(text = "Date" , color = Color.Gray , fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            Text(
                text = dateFormat(packageInfo.shipment.date),
                color = Color.Black ,
                fontSize = 14.sp ,
                fontWeight = FontWeight.Medium
            )
        }
        Row {
            Text(text = "Package Price" , color = Color.Gray , fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            Text(
                text = dateFormat(packageInfo.shipment.price.toString()),
                color = Color.Black ,
                fontSize = 14.sp ,
                fontWeight = FontWeight.Medium
            )
        }
        Row {
            Text(text = "Delivery Fee" , color = Color.Gray , fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            Text(
                text = dateFormat(packageInfo.shipment.deliveryFee),
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
