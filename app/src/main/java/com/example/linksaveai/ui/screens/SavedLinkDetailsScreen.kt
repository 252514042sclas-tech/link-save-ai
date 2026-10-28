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
fun SavedLinkDetailsScreen(
    linkItem: LinkItem,
    onBack: () -> Unit,
    onScanAgain: (String) -> Unit,
    onRemoveBookmark: (LinkItem) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Link Details") },
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
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = linkItem.domain, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Icon(imageVector = Icons.Default.Bookmark, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    }
                    Text(text = linkItem.url, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    HorizontalDivider()
                    Text(text = "Saved Date: ${linkItem.timestamp}")
                    Text(text = "Status: ${linkItem.status}")
                    Text(text = "Description: ${linkItem.details}")
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

            OutlinedButton(
                onClick = { onRemoveBookmark(linkItem); onBack() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Remove Bookmark")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedLinkDetailsScreenPreview() {
    LinkSaveAiTheme {
        SavedLinkDetailsScreen(
            linkItem = LinkItem("1", "https://github.com", "github.com", "Safe", "Today, 10:30 AM", true, "Verified platform."),
            onBack = {},
            onScanAgain = {},
            onRemoveBookmark = {}
        )
    }
}
