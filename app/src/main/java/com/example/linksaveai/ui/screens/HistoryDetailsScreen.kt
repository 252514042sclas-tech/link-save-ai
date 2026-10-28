package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linksaveai.data.LinkItem
import com.example.linksaveai.ui.theme.LinkSaveAiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailsScreen(
    linkItem: LinkItem,
    onBack: () -> Unit,
    onScanAgain: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("History Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (linkItem.status == "Safe") MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (linkItem.status == "Safe") Icons.Default.CheckCircle else Icons.Default.Warning,
                            contentDescription = null,
                            tint = if (linkItem.status == "Safe") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = linkItem.status,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = if (linkItem.status == "Safe") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = linkItem.domain, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = linkItem.url, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Scan Information", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    HorizontalDivider()
                    Text(text = "Scan Date: ${linkItem.timestamp}")
                    Text(text = "Details: ${linkItem.details}")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onScanAgain(linkItem.url) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Scan Again")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryDetailsScreenPreview() {
    LinkSaveAiTheme {
        HistoryDetailsScreen(
            linkItem = LinkItem("1", "https://google.com", "google.com", "Safe", "Today, 10:30 AM", true, "Valid SSL certificate."),
            onBack = {},
            onScanAgain = {}
        )
    }
}
