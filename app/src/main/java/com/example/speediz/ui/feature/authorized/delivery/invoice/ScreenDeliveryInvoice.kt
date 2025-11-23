package com.example.speediz.ui.feature.authorized.delivery.invoice

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.speediz.ui.feature.appwide.button.SelectDate
import com.example.speediz.ui.feature.appwide.button.SpDatePickerInput
import com.example.speediz.ui.theme.SpeedizTheme
import java.util.logging.SimpleFormatter

@Composable
fun ScreenDeliveryInvoice1(
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
) {
}
@Composable
fun ScreenDeliveryInvoice(
    invoices: List<InvoiceItem> = sampleInvoices()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(20.dp))

        // Top title row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Invoice",
                style = TextStyle(
                    fontSize = 22.sp ,
                    fontWeight = FontWeight.Bold ,
                    color = Color.Black
                )
            )
        }

        Spacer(Modifier.height(20.dp))

        // Search box
        SearchBox()

        Spacer(Modifier.height(16.dp))

        // Select date button
        Box(
            modifier = Modifier.width(200.dp)
                .align(Alignment.End),
        ) {
            SpDatePickerInput(
                placeholderText = "Select date",
                onValueChange = {}
            )
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(invoices) { item ->
                InvoiceCard(item)
                Spacer(Modifier.height(14.dp))
            }
        }
    }
}

@Composable
fun SearchBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE9E9E9), RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Icon(
                imageVector = Icons.Default.Search ,
                contentDescription = "" ,
                tint = Color.Gray
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = "Search shipment number",
                color = Color.Gray,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun InvoiceCard(item: InvoiceItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, Color(0xFFF2B246), RoundedCornerShape(16.dp))
            .background(Color(0x1AF2B246))
            .padding(16.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.ShoppingCart ,
                contentDescription = "" ,
                tint = Color(0xFFF2B246)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = "Invoice - ${item.number}" ,
                color = Color.Black ,
                fontSize = 16.sp ,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(10.dp))

        HorizontalDivider(thickness = 1.dp , color = Color(0xFFE7E7E7))

        Spacer(Modifier.height(10.dp))

        Text(text = "Date" , color = Color.Gray , fontSize = 14.sp)

        Spacer(Modifier.height(4.dp))

        Text(
            text = item.date ,
            color = Color.Black ,
            fontSize = 14.sp ,
            fontWeight = FontWeight.Medium
        )
    }
}
data class InvoiceItem(
    val number: String,
    val date: String
)

fun sampleInvoices() = List(5) {
    InvoiceItem(
        number = "00000001",
        date = "2025-09-29 11:32 PM"
    )
}
@Preview (showBackground = true)
@Composable
fun PreviewScreenDeliveryInvoice() {
    SpeedizTheme {
        ScreenDeliveryInvoice()
    }
}

