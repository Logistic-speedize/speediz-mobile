package com.example.speediz.ui.feature.authorized.delivery.history

import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.speediz.core.data.delivery.PackageHistoryResponse
import com.example.speediz.ui.feature.appwide.button.SPLoading
import com.example.speediz.ui.feature.appwide.button.SpDatePickerInput
import com.example.speediz.ui.utils.convertDateCalendar

@Composable
fun ScreenHistory(
    onBack: () -> Unit,
    onNavigateTo: (String) -> Unit = {}
) {
    val viewModel = hiltViewModel<HistoryVM>()
    val historyListState by viewModel.uiState.collectAsState()

    val tabs = listOf("ALL", "Completed", "Cancelled")
    var selectedTab by remember { mutableStateOf("ALL") }
    val dateQuery = remember { mutableStateOf("") }

    LaunchedEffect(selectedTab, dateQuery.value) {
        val date = dateQuery.value.takeIf { it.isNotBlank() }

        // Always filter by selected tab (unless ALL) and date
        val status = if (selectedTab == "ALL") null else selectedTab
        viewModel.filterPackageHistory(status = status, date = date)
    }
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .statusBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }

                    Text(
                        text = "Report",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.width(48.dp))
                }
            },
            containerColor = Color.Transparent
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {

                /** ---------------- Tabs ------------------- **/
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(top = 10.dp, bottom = 6.dp)
                ) {
                    tabs.forEach { tab ->
                        val selected = tab == selectedTab

                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(
                                    if (selected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surface
                                )
                                .clickable { selectedTab = tab }
                                .padding(horizontal = 22.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = tab,
                                fontWeight = FontWeight.SemiBold,
                                color = if (selected) Color.White else Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                /** ---------------- Select Dates Button ------------------- **/
                Box(
                    modifier = Modifier.width(200.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    SpDatePickerInput(
                        placeholderText = "Select date",
                        onValueChange = {
                            dateQuery.value = it
                        }
                    )
                }

                Spacer(Modifier.height(16.dp))
                when (historyListState) {
                    is HistoryState.Loading -> {
                        SPLoading()
                    }
                    is HistoryState.Success -> {
                        val items = (historyListState as HistoryState.Success).filteredItems
                        /** ---------------- Cards ------------------- **/
                        if(items.isEmpty()){
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ){
                                Text(
                                    text = "No Items Available",
                                    modifier = Modifier.align(Alignment.Center),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }else{
                            LazyColumn {
                                items(items.size) { index ->
                                    HistoryCard(
                                        historyDetail = items[index],
                                        onClick = { id -> onNavigateTo(id) }
                                    )
                                    Spacer(Modifier.height(18.dp))
                                }
                            }
                        }
                    }
                    else -> {

                    }
                }
            }
        }
}


@Composable
fun HistoryCard(
    historyDetail: PackageHistoryResponse.PackageHistoryData.PackageHistoryItem,
    onClick: (String) -> Unit,
) {

    val statusColor = when (historyDetail.status.lowercase()) {
        "completed" -> Color.Green
        else -> Color.Red
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(24.dp))
            .background(Color.White)
            .padding(20.dp)
    ) {

        Column {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "#SP${historyDetail.id}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Text(
                            text = convertDateCalendar(historyDetail.shipmentInfo.date, "yyyy-MM-dd", "dd-MM-yyyy"),
                            fontSize = 14.sp,
                            color = Color.LightGray
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(statusColor)
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = historyDetail.status.replaceFirstChar { it.uppercase() },
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            Row {
                Column(Modifier.weight(1f)) {
                    Text("From", fontSize = 12.sp, color = Color.Gray)
                    Text(
                        "${historyDetail.vendorInfo.firstName} ${historyDetail.vendorInfo.lastName}",
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(8.dp))
                    Text("Sender’s Number", fontSize = 12.sp, color = Color.Gray)
                    Text(historyDetail.vendorInfo.contactNumber)
                }

                Column(Modifier.weight(1f)) {
                    Text("To", fontSize = 12.sp, color = Color.Gray)
                    Text(
                        "${historyDetail.customerInfo.firstName} ${historyDetail.customerInfo.lastName}",
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.height(8.dp))
                    Text("Receiver’s Number", fontSize = 12.sp, color = Color.Gray)
                    Text(historyDetail.customerInfo.contactNumber)
                }
            }

            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text("Location", fontSize = 12.sp, color = Color.Gray)
                Text(historyDetail.locationInfo.location)
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = { onClick(historyDetail.id.toString()) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("VIEW DETAILS", fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }
    }
}

