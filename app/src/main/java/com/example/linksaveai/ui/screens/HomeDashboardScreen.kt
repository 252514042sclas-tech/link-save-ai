package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.linksaveai.ui.components.HistoryItemCard
import com.example.linksaveai.ui.components.StatCard
import com.example.linksaveai.ui.theme.LinkSaveAiTheme

@Composable
fun HomeDashboardScreen(
    history: List<LinkItem> = emptyList(),
    onScanUrlClicked: (String) -> Unit = {},
    onNavigateToScanner: () -> Unit = {},
    onNavigateToHistory: () -> Unit = {},
    onNavigateToSaved: () -> Unit = {},
    onNavigateToTips: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onItemClick: (LinkItem) -> Unit = {}
) {
    var urlInput by remember { mutableStateOf("") }
    val safeCount = history.count { it.status == "Safe" }
    val suspiciousCount = history.count { it.status == "Suspicious" }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Hello, Explorer 👋", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Text(text = "Stay Safe. Click Smart.", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                IconButton(onClick = onNavigateToSettings) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Safe Links",
                    count = safeCount.toString(),
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Suspicious",
                    count = suspiciousCount.toString(),
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.error,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Scan a New Link", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = urlInput,
                        onValueChange = { urlInput = it },
                        placeholder = { Text("Paste URL here (e.g. https://...)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            if (urlInput.isNotBlank()) {
                                onScanUrlClicked(urlInput)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Scan URL Now")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Recent Scans", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                TextButton(onClick = onNavigateToHistory) {
                    Text(text = "View All")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(history.take(5)) { item ->
                    HistoryItemCard(item = item, onClick = { onItemClick(item) })
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToHistory,
                    icon = { Icon(Icons.Default.History, contentDescription = "History") },
                    label = { Text("History") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToSaved,
                    icon = { Icon(Icons.Default.Bookmark, contentDescription = "Saved") },
                    label = { Text("Saved") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToTips,
                    icon = { Icon(Icons.Default.Lightbulb, contentDescription = "Tips") },
                    label = { Text("Tips") }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeDashboardScreenPreview() {
    LinkSaveAiTheme {
        HomeDashboardScreen(
            history = listOf(
                LinkItem("1", "https://google.com", "google.com", "Safe", "Today, 10:30 AM", true)
            )
        )
    }
}
