package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
fun ScanSummaryScreen(
    history: List<LinkItem>,
    onBack: () -> Unit
) {
    val totalScans = history.size
    val safeCount = history.count { it.status == "Safe" }
    val suspiciousCount = history.count { it.status == "Suspicious" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scan Summary & Statistics") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = "Overview Statistics", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    HorizontalDivider()
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total Scans")
                        Text(totalScans.toString(), fontWeight = FontWeight.Bold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Safe Links", color = MaterialTheme.colorScheme.primary)
                        Text(safeCount.toString(), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Suspicious Links", color = MaterialTheme.colorScheme.error)
                        Text(suspiciousCount.toString(), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                    }
                }
            }

            if (history.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    Text("No scan data available (Showing sample statistics if offline)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScanSummaryScreenPreview() {
    LinkSaveAiTheme {
        ScanSummaryScreen(
            history = listOf(
                LinkItem("1", "https://google.com", "google.com", "Safe", "Today", true),
                LinkItem("2", "http://bad.com", "bad.com", "Suspicious", "Yesterday", false)
            ),
            onBack = {}
        )
    }
}
