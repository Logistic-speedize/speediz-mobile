package com.example.speediz.ui.feature.authorized.delivery.invoice

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.speediz.core.data.delivery.InvoiceResponse
import com.example.speediz.ui.feature.appwide.button.SPLoading
import com.example.speediz.ui.feature.appwide.button.SpDatePickerInput
import com.example.speediz.ui.utils.dateFormat

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenDeliveryInvoice(
    onNavigateTo: (String) -> Unit,
    onBackPress: () -> Unit,
) {
    val viewModel = hiltViewModel<DeliveryInvoiceViewModel>()
    val responseUiState = viewModel.invoiceUIState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var dateFilter by remember { mutableStateOf("") }
    LaunchedEffect(viewModel) {
        viewModel.fetchInvoiceData()
    }
    LaunchedEffect(searchQuery, dateFilter) {
        viewModel.fetchInvoiceData(
            searchQuery = searchQuery.ifBlank { null },
            date = dateFilter.ifBlank { null }
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Invoices",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    )
                },
                navigationIcon = {
                    Icon(
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
        },
        modifier = Modifier.fillMaxSize().statusBarsPadding(),
        containerColor = Color.Transparent

    ) {
        innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            // Search box
            SearchBox(
                value = searchQuery,
                onChange = {
                    searchQuery = it
                }
            )
            Spacer(Modifier.height(16.dp))
            // Select date button
            Box(
                modifier = Modifier.width(200.dp)
                    .height(60.dp)
                    .align(Alignment.End),
            ) {
                SpDatePickerInput(
                    placeholderText = "Select date",
                    onValueChange = {
                        dateFilter = it
                    },
                )
            }
            Spacer(Modifier.height(16.dp))
            when (val state = responseUiState.value) {
                is DeliveryInvoiceUiState.Loading -> {
                    SPLoading()
                }
                is DeliveryInvoiceUiState.Success -> {
                    if (state.invoiceUiState.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "No invoices found.", color = Color.Gray)
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
                        ) {
                            items(state.invoiceUiState) { item ->
                                InvoiceCard(
                                    item = item,
                                    toDetail = onNavigateTo
                                )
                            }
                        }
                    }
                }
                is DeliveryInvoiceUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = state.message, color = Color.Red)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBox(
    onChange: (String) -> Unit = { },
    value: String = "",
    placeHolder: String = "Search invoice..."
) {
    val displayText = rememberSaveable { mutableStateOf(value) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        TextField(
            value = displayText.value,
            onValueChange = {
                displayText.value = it
                onChange(it)
            },
            placeholder = {
                Text(
                    text = placeHolder,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, MaterialTheme.colorScheme.inverseSurface.copy(0.5f), RoundedCornerShape(20.dp)),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InvoiceCard(
    item : InvoiceResponse.Data.InvoiceItems,
    toDetail: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, androidx.compose.material3.MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .background(androidx.compose.material3.MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .clickable{
                toDetail(item.id.toString())
            }
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.ShoppingCart ,
                contentDescription = "" ,
                tint = androidx.compose.material3.MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = "Invoice - ${item.invoiceNumber}" ,
                color = Color.Black ,
                fontSize = 16.sp ,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(10.dp))

        HorizontalDivider(thickness = 1.dp , color = androidx.compose.material3.MaterialTheme.colorScheme.inverseSurface.copy(0.3f))

        Spacer(Modifier.height(10.dp))

        Row {
            Text(text = "Date" , color = Color.Gray , fontSize = 14.sp)
            Spacer(Modifier.weight(1f))
            Text(
                text = dateFormat(item.invoiceDate),
                color = Color.Black ,
                fontSize = 14.sp ,
                fontWeight = FontWeight.Medium
            )
        }
    }
}



