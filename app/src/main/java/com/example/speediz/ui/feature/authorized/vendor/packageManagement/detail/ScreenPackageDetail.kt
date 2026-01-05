package com.example.speediz.ui.feature.authorized.vendor.packageManagement.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.speediz.ui.feature.appwide.button.SPLoading
import com.example.speediz.ui.feature.authorized.vendor.packageTracking.detail.PackageDetail
import com.example.speediz.ui.feature.authorized.vendor.packageTracking.detail.PackageUiState
import com.example.speediz.ui.theme.Transparent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenPackageDetail(
    id: String,
    onBack: () -> Unit
) {
    val viewModel = hiltViewModel<PackageDetailViewModel>()
    val uiState = viewModel.packageUiState.collectAsState()
    val packageDetail = viewModel.packageDetail.collectAsState().value
    LaunchedEffect(id) {
        viewModel.fetchPackageDetail(id)
        Log.d( "ScreenPackageDetail" , "${packageDetail}" )
    }
    LaunchedEffect(uiState.value) {
        when(uiState.value){
            is PackageUiState.Loading -> {
                viewModel.fetchPackageDetail(id)
                Log.d( "ScreenPackageDetail" , "Fetching package detail for ID: $id" )
            }
            else -> {}
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    androidx.compose.material3.Text(
                        text = "Package Detail",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    androidx.compose.material3.IconButton(
                        onClick = { onBack() }
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        containerColor = Transparent
    ) { paddingValues ->
        when (val state = uiState.value) {
            is PackageUiState.Loading -> {
                SPLoading()
            }
            is PackageUiState.Success -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    PackageDetail(
                        packageDetail = state.detail.data,
                        status = state.detail.data.packageDetail.status,
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.background
                        )
                    )
                }
            }
            else -> {}
        }
    }
}