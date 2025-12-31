package com.example.speediz.ui.feature.authorized.delivery.express

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.speediz.R
import com.example.speediz.core.data.delivery.ExpressResponse
import com.example.speediz.ui.feature.authorized.delivery.invoice.SearchBox
import com.example.speediz.ui.theme.SPColor

@Composable
fun ScreenExpress(
    onNavigateTo: (String) -> Unit ,
    onBack: () -> Unit
) {
    val viewModel = hiltViewModel<ExpressViewModel>()
    val filteredList= viewModel.expressFilter.collectAsState()
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchExpressData()
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .padding(8.dp),
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onBack() }
                    ,
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                    tint = Color.Black
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Express",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.Black
                )
            }
        }
    ) {
            padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAF6))
                .padding(padding)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // --- Search Bar with Search Icon ---
            SearchBox(
                onChange = {
                    searchText = it
                    val idText = searchText.filter { char -> char.isDigit() }
                    viewModel.searchExpressById(idText)
                }
            )

            Spacer(modifier = Modifier.height(28.dp))

            // --- Packages List ---
            LazyColumn(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                if ( filteredList.value.isEmpty()){
                    item {
                        Text(
                            text = "No packages found.",
                            fontWeight = FontWeight.Bold ,
                            fontSize = 18.sp ,
                            color = Color.Black,
                            modifier = Modifier.fillMaxSize().align(alignment = Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    filteredList.value.forEach { ( date, items) ->
                        item {
                            Text(
                                text = date,
                                fontWeight = FontWeight.Bold ,
                                fontSize = 18.sp ,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        items(items) { item ->
                            ExpressCard(item = item, onNavigateTo = {
                                onNavigateTo(item.id.toString())
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExpressCard(item: ExpressResponse.Data.ExpressItems, onNavigateTo : () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, color = Color(0xFFFFC64B), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "#SPD${item.id}",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                color = Color.Black
            )
            Box(
                modifier = Modifier
                    .background(
                        when (item.status) {
                            "in_transit" -> SPColor.blueInfo
                            "completed" -> SPColor.greenSuccess
                            "cancelled" -> SPColor.error
                            else -> SPColor.yellowWarning
                        } ,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                val statusText = when (item.status) {
                    "in_transit" -> "In Transit"
                    "completed" -> "Completed"
                    "cancelled" -> "Cancelled"
                    else -> "Pending"
                }
                Text(
                    text = statusText,
                    color = Color.White,
                    fontSize = 14.sp,
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
                val vendor = "${item.vendor?.firstName} ${item.vendor?.lastName}"
                Text(vendor, fontWeight = FontWeight.Medium, color = Color.Black)
            }
            Column(
                horizontalAlignment = Alignment.End,
            ) {
                Text("To", fontSize = 13.sp, color = Color.Gray)
                val customer = "${item.customer?.firstName} ${item.customer?.lastName}"
                Text(customer , fontWeight = FontWeight.Medium, color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column{
                Text("Sender’s Number", fontSize = 13.sp, color = Color.Gray)
                Text(item.vendor?.contactNumber.toString(), fontWeight = FontWeight.Medium, color = Color.Black)
            }
            Column(
                horizontalAlignment = Alignment.End,
            ) {
                Text("Receiver’s Number", fontSize = 13.sp, color = Color.Gray)
                Text(item.customer?.phone.toString(), fontWeight = FontWeight.Medium, color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Location", color = Color.Gray, fontSize = 14.sp)
            Text(
                text = item.location?.location.toString(),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onNavigateTo()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC64B)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("VIEW DETAILS", fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

