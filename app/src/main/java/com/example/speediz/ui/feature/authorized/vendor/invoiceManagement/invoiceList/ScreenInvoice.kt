// Kotlin
package com.example.speediz.ui.feature.authorized.vendor.invoiceManagement.invoiceList

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.speediz.R
import com.example.speediz.ui.feature.authorized.delivery.invoice.SearchBox
import com.example.speediz.ui.utils.SpeedizPaidStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenInvoice(
    onNavigateTo: (String) -> Unit,
    onBack: () -> Unit
) {
    val viewModel = hiltViewModel<InvoiceListViewModel>()
    val invoiceUiState by viewModel.isInvoiceUiState.collectAsState()

    var selectedFilter by remember { mutableStateOf(PaymentFilter.ALL) }
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(searchText, selectedFilter) {
        viewModel.fetchAndFilterInvoices(
            searchId = searchText,
            filter = selectedFilter
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Invoice Management", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "Back",
                        modifier = Modifier.size(30.dp).clickable { onBack() }
                    )
                }
            )
        },
        containerColor = Color.Transparent
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(it)) {

            // Search box
            SearchBox(
                value = searchText,
                onChange = { newText ->
                    searchText = newText
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tab Filter
            FilterRow(
                options = listOf("All", "Unpaid", "Paid"),
                selectedOption = when (selectedFilter) {
                    PaymentFilter.ALL -> "All"
                    PaymentFilter.UNPAID -> "Unpaid"
                    PaymentFilter.PAID -> "Paid"
                },
                onOptionSelected = { option ->
                    selectedFilter = when(option) {
                        "Paid" -> PaymentFilter.PAID
                        "Unpaid" -> PaymentFilter.UNPAID
                        else -> PaymentFilter.ALL
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (invoiceUiState) {
                is InvoiceUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is InvoiceUiState.Success -> {
                    val invoices = invoiceUiState as InvoiceUiState.Success
                    // Invoices Listing
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(invoices.data) { invoice ->
                            val statusColor = when (invoice.status.lowercase()) {
                                SpeedizPaidStatus.Paid.status.lowercase() -> Color(0xFF4CAF50)
                                SpeedizPaidStatus.Unpaid.status.lowercase() -> Color(0xFFF44336)
                                else -> Color.Gray
                            }

                            InvoiceItem(
                                invoiceId = invoice.invoiceNumber.toString(),
                                status = invoice.status,
                                statusColor = statusColor,
                                onClick = {
                                    onNavigateTo(invoice.id.toString())
                                }
                            )
                        }
                    }
                }
                else -> {
                    // Handle other states if necessary
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun FilterRowPreview() {
    var selectedOption by remember { mutableStateOf("All") }
    FilterRow(
        selectedOption = selectedOption,
        onOptionSelected = { selectedOption = it }
    )
}

@Composable
fun FilterRow(
    options: List<String> = listOf("All", "Unpaid", "Paid"),
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEach { option ->
            val isSelected = option == selectedOption
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(36.dp)
                    .background(
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { onOptionSelected(option) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    color = if (isSelected) Color.White else Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview
@Composable
fun InvoiceItem(
    modifier: Modifier = Modifier,
    invoiceId: String = "1",
    status: String = "Paid",
    statusColor: Color = Color(0xFF4CAF50),
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.95f),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(4.dp))
                        .background(Color.White, shape = RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_invoice),
                        contentDescription = "Invoice Icon",
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.95f),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = invoiceId,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = "Forward",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }

            Divider(
                color = Color.Gray.copy(alpha = 0.3f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Status",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .size(width = 80.dp, height = 24.dp)
                        .background(color = statusColor, shape = RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = status.uppercase(),
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}
