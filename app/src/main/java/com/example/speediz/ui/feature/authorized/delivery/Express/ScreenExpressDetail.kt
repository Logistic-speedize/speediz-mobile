package com.example.speediz.ui.feature.authorized.delivery.Express

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speediz.R
import com.example.speediz.ui.theme.SpeedizTheme

@Composable
fun ScreenExpressDetail2() {
    var currentStatus by remember { mutableStateOf("Shipping") } // simulate current status

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAF6))
    ) {
        // --- Top Bar (outside sheet) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Back navigation */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Express",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- Bottom Sheet ---
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAF6))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
                    )
                    .padding(20.dp)
            ) {
                // Handle bar
                Box(
                    modifier = Modifier
                        .size(width = 60.dp, height = 8.dp)
                        .background(Color(0xFFC2C2C2), RoundedCornerShape(50))
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Package Info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("#SDP1245", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text("Buern Salang, Phnom Penh", fontSize = 15.sp, color = Color.Gray)
                    }
                    Box(
                        modifier = Modifier
                            .background(
                                Color(0xFF4AA8F8),
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text("On hold", color = Color.White, fontSize = 14.sp)
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
                        title = "Packed",
                        active = currentStatus == "Packed" || currentStatus == "Shipping" || currentStatus == "Delivered"
                    )
                    StatusItem(
                        title = "In_Transit",
                        active = currentStatus == ""
                    )
                    StatusItem(
                        title = "Delivered",
                        active = currentStatus == "Delivered"
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
                        Text("David Laid", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                        Text("Sender’s Number", fontSize = 13.sp, color = Color.Gray)
                        Text("01249374386", fontWeight = FontWeight.Medium)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("To", fontSize = 13.sp, color = Color.Gray)
                        Text("Yongg", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                        Text("Receiver’s Number", fontSize = 13.sp, color = Color.Gray)
                        Text("01249374386", fontWeight = FontWeight.Medium)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Price Info
                Column {
                    Text("Package price(\$):  \$100.00", fontWeight = FontWeight.Bold)
                    Text("Package price(riel):  410000.00", fontWeight = FontWeight.Bold)
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExpressDetailPreview() {
    SpeedizTheme {
        ScreenExpressDetail2()
    }
}

