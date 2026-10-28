package com.example.linksaveai.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linksaveai.ui.theme.LinkSaveAiTheme

data class FaqItem(val question: String, val answer: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpFaqScreen(
    onBack: () -> Unit
) {
    val faqs = listOf(
        FaqItem("How do I scan a URL?", "Paste any URL into the input field on the Home screen and tap 'Scan URL Now'."),
        FaqItem("What do suspicious results mean?", "A suspicious result indicates unencrypted HTTP or risky domain patterns commonly associated with phishing."),
        FaqItem("Can I remove saved links?", "Yes, open Saved Links or Saved Link Details and tap Remove Bookmark."),
        FaqItem("Is my data secure?", "All scan histories are stored locally on your device to ensure maximum privacy.")
    )

    var expandedIndex by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help & FAQ") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(faqs.indices.toList()) { index ->
                val faq = faqs[index]
                val isExpanded = expandedIndex == index

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandedIndex = if (isExpanded) null else index },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = faq.question, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null
                            )
                        }
                        if (isExpanded) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = faq.answer, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HelpFaqScreenPreview() {
    LinkSaveAiTheme {
        HelpFaqScreen(onBack = {})
    }
}
