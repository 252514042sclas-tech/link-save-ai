package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Warning
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
fun SuspiciousResultScreen(
    linkItem: LinkItem = LinkItem("2", "http://free-prize-win.xyz/login", "free-prize-win.xyz", "Suspicious", "Yesterday, 4:15 PM", false, "Unencrypted HTTP, suspicious top-level domain."),
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
                    color = Color(0xFFFFEBEE),
                    modifier = Modifier.size(100.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Warning Icon",
                            tint = Color(0xFFC62828),
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Suspicious Link Detected!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFC62828),
                    textAlign = TextAlign.Center
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
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Warning Signs", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFFC62828))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "• Domain: ${linkItem.domain}", fontSize = 14.sp)
                        Text(text = "• Indicator: ${linkItem.details}", fontSize = 14.sp)
                        Text(text = "• Advice: Do not open this link or enter personal credentials until verified.", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onViewDetails,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
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
fun SuspiciousResultScreenPreview() {
    LinkSaveAiTheme {
        SuspiciousResultScreen()
    }
}
