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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.speediz.R
import com.example.speediz.ui.feature.authorized.delivery.invoice.DeliveryInvoiceViewModel

@Composable
fun ScreenInvoice(
    onNavigateTo: (String) -> Unit,
    onBack: () -> Unit
) {
    val viewModel = hiltViewModel<InvoiceListViewModel>()
    val invoices by viewModel.responseUIState.collectAsState()
    val isLoading by viewModel.loading.collectAsState()

    var selectedFilter by remember { mutableStateOf("All") }
    val searchQuery by viewModel.searchQuery.collectAsState()

    // Fetch data when the screen appears
    LaunchedEffect(viewModel) {
        viewModel.fetchInvoiceData()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Header { onBack() }

        Spacer(modifier = Modifier.height(16.dp))

        SearchBar(
            query = searchQuery,
            onQueryChange = { /* TO DO */}
        )

        Spacer(modifier = Modifier.height(16.dp))

        FilterRow(
            options = listOf("All", "Unpaid", "Paid"),
            selectedOption = selectedFilter,
            onOptionSelected = { option ->
                selectedFilter = option
                // You can also call a ViewModel function to filter data
                // viewModel.filterByStatus(option)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(invoices) { invoice ->
                    InvoiceItem(
                        invoiceId = invoice.id.toString(),
                        status = invoice.status,
                        statusColor = when (invoice.status.lowercase()) {
                            "paid" -> Color(0xFF4CAF50)
                            "unpaid" -> Color(0xFFFFC107)
                            else -> Color.Gray
                        },
                        onClick = { onNavigateTo(invoice.id.toString()) }
                    )
                }
            }
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
                text = "Invoice Management",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Placeholder Spacer
        Spacer(modifier = Modifier.size(30.dp))
    }
}


@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp)
            .shadow(4.dp, shape = RoundedCornerShape(12.dp))
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp), // ensure textfield stays same height
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
                    decorationBox = { innerTextField ->
                        if (query.isEmpty()) {
                            Text(
                                text = "Search invoice id",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                )
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
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
                    text = option, // make text capital
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
            // Top Row: Icon + Invoice ID + Arrow
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .shadow(
                            elevation = 2.dp,
                            shape = RoundedCornerShape(4.dp)
                        )
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
                    text = "Invoice - ${invoiceId}",
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

            // Bottom Row: Status
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
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
                        .background(
                            color = statusColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
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