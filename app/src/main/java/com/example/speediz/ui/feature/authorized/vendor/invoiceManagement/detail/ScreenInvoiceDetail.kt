package com.example.speediz.ui.feature.authorized.vendor.invoiceManagement.detail

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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speediz.R

@Composable
fun ScreenInvoiceDetail(
    id: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header { onBack() }

//        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 0.dp)
        ) {
            // Display invoice info dynamically using the `id`
            Text(text = "Invoice No: $id", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Date: 2025-12-01", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Pending Packages: 2", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Completed Packages: 5", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Cancelled Packages: 1", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Status: PAID", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun Header(
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back Icon
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "Back",
            modifier = Modifier
                .size(30.dp)
                .clickable { onBackClick() }
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Title
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Invoice Information",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Placeholder Spacer
        Spacer(modifier = Modifier.size(30.dp))
    }
}