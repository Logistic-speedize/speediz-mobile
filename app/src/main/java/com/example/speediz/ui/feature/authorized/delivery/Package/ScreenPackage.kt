package com.example.speediz.ui.feature.authorized.delivery.Package

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speediz.R
import com.example.speediz.ui.theme.SpeedizTheme

@Composable
fun ScreenPackage() {
    val packages = listOf(
        PackageItem(
            id = "#SDP1245",
            senderName = "David Laid",
            receiverName = "Yongg",
            senderNumber = "01249374386",
            receiverNumber = "01249374386",
            location = "Buerng Salang, Phnom Penh",
            status = "On hold",
            statusColor = Color(0xFF4AA8F8)
        ),
        PackageItem(
            id = "#SDP1245",
            senderName = "Jeff Clurry",
            receiverName = "Yongg",
            senderNumber = "01249374386",
            receiverNumber = "01249374386",
            location = "Buerng Salang, Phnom Penh",
            status = "Complete",
            statusColor = Color(0xFF4CAF50)
        )
    )

    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAF6))
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // --- Top Bar with Arrow and Title ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Back",
                tint = Color.Black
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Express",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // --- Search Bar with Search Icon ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(12.dp))
                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                BasicTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    textStyle = TextStyle(fontSize = 16.sp),
                    decorationBox = { innerTextField ->
                        if (searchText.text.isEmpty()) {
                            Text(
                                "Search package phone number",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // --- Date Header ---
        Text(
            text = "May 12, 2024",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        // --- Packages List ---
        LazyColumn(verticalArrangement = Arrangement.spacedBy(18.dp)) {
            items(packages) { item ->
                PackageCard(item)
            }
        }
    }
}

@Composable
fun PackageCard(item: PackageItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(2.dp, color = Color(0xFFFFC64B), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.id,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )
            Box(
                modifier = Modifier
                    .background(item.statusColor, RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = item.status,
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text("From", fontSize = 13.sp, color = Color.Gray)
                Text(item.senderName, fontWeight = FontWeight.Medium)
            }
            Column {
                Text("To", fontSize = 13.sp, color = Color.Gray)
                Text(item.receiverName, fontWeight = FontWeight.Medium)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text("Sender’s Number", fontSize = 13.sp, color = Color.Gray)
                Text(item.senderNumber, fontWeight = FontWeight.Medium)
            }
            Column {
                Text("Receiver’s Number", fontSize = 13.sp, color = Color.Gray)
                Text(item.receiverNumber, fontWeight = FontWeight.Medium)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Location", color = Color.Gray, fontSize = 14.sp)
            Text(
                text = item.location,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* handle details click */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC64B)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("VIEW DETAILS", fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

// --- Data model ---
data class PackageItem(
    val id: String,
    val senderName: String,
    val receiverName: String,
    val senderNumber: String,
    val receiverNumber: String,
    val location: String,
    val status: String,
    val statusColor: Color
)

@Preview(showBackground = true)
@Composable
fun PackagePreview() {
    SpeedizTheme {
        ScreenPackage()
    }
}