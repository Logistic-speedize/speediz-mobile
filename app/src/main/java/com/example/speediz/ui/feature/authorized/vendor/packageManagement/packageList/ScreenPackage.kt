package com.example.speediz.ui.feature.authorized.vendor.packageManagement.packageList

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.speediz.R
import com.example.speediz.ui.feature.appwide.button.SPLoading
import com.example.speediz.ui.feature.authorized.delivery.invoice.SearchBox
import com.example.speediz.ui.feature.authorized.vendor.packageTracking.PackageCard
import com.example.speediz.ui.feature.authorized.vendor.packageTracking.PackageTrackingViewModel
import com.example.speediz.ui.feature.authorized.vendor.packageTracking.PackageUiState

@Composable
fun ScreenPackage(
    onNavigateTo: (String) -> Unit,
    onNavigateToCreatePackage: () -> Unit,
    onBack: () -> Unit
){
    val viewModel = hiltViewModel<PackageTrackingViewModel>()
    val packageUiState = viewModel.packageUiState.collectAsState()
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchPackageList()
    }
    LaunchedEffect(searchText) {
        viewModel.fetchPackageList(searchText)
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
                    text = "Package Management",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.Black
                )
            }
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .shadow(4.dp , RoundedCornerShape(28.dp))
                    .background(MaterialTheme.colorScheme.primary , RoundedCornerShape(28.dp))
                    .clickable { onNavigateToCreatePackage() }
                ,
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Package",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
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
                },
                placeHolder = "Search by id"
            )

            Spacer(modifier = Modifier.height(28.dp))

            // --- Packages List ---
           when (val state = packageUiState.value) {
                is PackageUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        SPLoading()
                    }
                }
                is PackageUiState.Success -> {
                    val filteredList = state.data
                    if (filteredList.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No packages found",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                            if ( filteredList.isEmpty()){
                                item {
                                    Text(
                                        text = "No packages found.",
                                        fontWeight = FontWeight.Bold ,
                                        fontSize = 18.sp ,
                                        color = Color.Black
                                    )
                                }
                            } else {
                                items(filteredList) { item ->
                                    PackageCard(
                                        item = item,
                                        onNavigateTo = { onNavigateTo(item.id.toString()) }
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                }
                is PackageUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.message,
                            fontSize = 16.sp,
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }
}