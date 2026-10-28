package com.example.linksaveai.ui.screens

import androidx.compose.foundation.clickable
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
import com.example.linksaveai.ui.theme.LinkSaveAiTheme

@Composable
fun HomeScreen(
    history: List<LinkItem>,
    onScanUrlClicked: (String) -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToSaved: () -> Unit,
    onNavigateToTips: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onItemClick: (LinkItem) -> Unit
) {
    var urlInput by remember { mutableStateOf("") }
    val totalCount = history.size
    val safeCount = history.count { it.status == "Safe" }
    val suspiciousCount = history.count { it.status == "Suspicious" }
    val unverifiedCount = history.count { it.status == "Unable to Verify" }
    val hasSuspicious = suspiciousCount > 0

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Top Bar / Greeting
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = "LinkSafe AI", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        Text(text = "AI Threat Protection Active", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }
                IconButton(onClick = onNavigateToSettings) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings", tint = MaterialTheme.colorScheme.onBackground)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Security Overview Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = MaterialTheme.shapes.large
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (hasSuspicious) Icons.Default.Warning else Icons.Default.VerifiedUser,
                        contentDescription = null,
                        tint = if (hasSuspicious) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (hasSuspicious) "Security Alert Detected" else "Device Fully Protected",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = if (hasSuspicious) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (hasSuspicious) "$suspiciousCount suspicious link(s) found." else "Heuristic & ML threat engine active.",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Scan Input Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Scan New URL", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = urlInput,
                        onValueChange = { urlInput = it },
                        placeholder = { Text("Paste URL here (e.g., https://...)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            if (urlInput.isNotBlank()) {
                                onScanUrlClicked(urlInput)
                                urlInput = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Analyze Link with AI")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Statistics Summary Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    title = "Analyzed",
                    count = totalCount.toString(),
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Safe",
                    count = safeCount.toString(),
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Risky",
                    count = suspiciousCount.toString(),
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.error,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Recent Scans Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Recent Activity", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
                TextButton(onClick = onNavigateToHistory) {
                    Text(text = "View All")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Recent Scans List or Empty State
            if (history.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.History, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "No scan history yet", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(history.take(4)) { item ->
                        HistoryItemCard(item = item, onClick = { onItemClick(item) })
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(title: String, count: String, containerColor: androidx.compose.ui.graphics.Color, contentColor: androidx.compose.ui.graphics.Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = count, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = contentColor)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = contentColor)
        }
    }
}

@Composable
fun HistoryItemCard(item: LinkItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when (item.status) {
                    "Safe" -> Icons.Default.CheckCircle
                    "Suspicious" -> Icons.Default.Warning
                    else -> Icons.Default.HelpOutline
                },
                contentDescription = null,
                tint = when (item.status) {
                    "Safe" -> MaterialTheme.colorScheme.primary
                    "Suspicious" -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.secondary
                },
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.domain, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = item.timestamp, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Surface(
                shape = MaterialTheme.shapes.small,
                color = when (item.status) {
                    "Safe" -> MaterialTheme.colorScheme.primaryContainer
                    "Suspicious" -> MaterialTheme.colorScheme.errorContainer
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }
            ) {
                Text(
                    text = item.status,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = when (item.status) {
                        "Safe" -> MaterialTheme.colorScheme.primary
                        "Suspicious" -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    LinkSaveAiTheme {
        HomeScreen(
            history = listOf(
                LinkItem("1", "https://google.com", "google.com", "Safe", "Today, 10:30 AM", true, "Valid SSL certificate."),
                LinkItem("2", "http://suspicious.xyz", "suspicious.xyz", "Suspicious", "Today, 09:15 AM", false, "Risky TLD.")
            ),
            onScanUrlClicked = {},
            onNavigateToScanner = {},
            onNavigateToHistory = {},
            onNavigateToSaved = {},
            onNavigateToTips = {},
            onNavigateToSettings = {},
            onItemClick = {}
        )
    }
}
