package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linksaveai.data.LinkItem
import com.example.linksaveai.ui.theme.LinkSaveAiTheme

@Composable
fun SafeResultScreen(
    linkItem: LinkItem = LinkItem("1", "https://google.com", "google.com", "Safe", "Today, 10:30 AM", true, "Valid SSL certificate."),
    onToggleSave: (LinkItem) -> Unit = {},
    onViewDetails: () -> Unit = {},
    onHome: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = Color(0xFFE8F5E9),
                    modifier = Modifier.size(100.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Safe Shield",
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Link is Safe",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = linkItem.url,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Scan Summary", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "• Domain: ${linkItem.domain}", fontSize = 14.sp)
                        Text(text = "• Scanned at: ${linkItem.timestamp}", fontSize = 14.sp)
                        Text(text = "• Note: DEMO Analysis result. Always verify sensitive links.", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onViewDetails,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("View Detailed Report")
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { onToggleSave(linkItem) },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Icon(
                            imageVector = if (linkItem.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (linkItem.isSaved) "Saved" else "Save Link")
                    }
                    OutlinedButton(
                        onClick = onHome,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Back to Home")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SafeResultScreenPreview() {
    LinkSaveAiTheme {
        SafeResultScreen()
    }
}
