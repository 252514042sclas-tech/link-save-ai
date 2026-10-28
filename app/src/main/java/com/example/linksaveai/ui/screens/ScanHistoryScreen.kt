package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linksaveai.data.LinkItem
import com.example.linksaveai.ui.components.HistoryItemCard
import com.example.linksaveai.ui.theme.LinkSaveAiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanHistoryScreen(
    history: List<LinkItem> = listOf(
        LinkItem("1", "https://google.com", "google.com", "Safe", "Today, 10:30 AM", true),
        LinkItem("2", "http://free-prize-win.xyz", "free-prize-win.xyz", "Suspicious", "Yesterday, 4:15 PM", false)
    ),
    onBack: () -> Unit = {},
    onClearHistory: () -> Unit = {},
    onItemClick: (LinkItem) -> Unit = {}
) {
    var selectedFilter by remember { mutableStateOf("All") }
    val filteredList = when (selectedFilter) {
        "Safe" -> history.filter { it.status == "Safe" }
        "Suspicious" -> history.filter { it.status == "Suspicious" }
        else -> history
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scan History") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (history.isNotEmpty()) {
                        IconButton(onClick = onClearHistory) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Clear History")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Safe", "Suspicious").forEach { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No scan history found.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 16.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredList) { item ->
                        HistoryItemCard(item = item, onClick = { onItemClick(item) })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScanHistoryScreenPreview() {
    LinkSaveAiTheme {
        ScanHistoryScreen()
    }
}
