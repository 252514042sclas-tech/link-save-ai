package com.example.linksaveai.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linksaveai.ui.theme.LinkSaveAiTheme

data class LearningArticle(
    val id: String,
    val title: String,
    val category: String,
    val summary: String,
    val content: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningCenterScreen(
    onBack: () -> Unit
) {
    val articles = listOf(
        LearningArticle("1", "Understanding Phishing", "Basics", "How attackers disguise themselves as trusted institutions.", "Phishing is a cybercrime in which a target or targets are contacted by email, telephone or text message by someone posing as a legitimate institution to lure individuals into providing sensitive data."),
        LearningArticle("2", "Why HTTPS Matters", "Security", "The importance of SSL/TLS encryption in web browsing.", "HTTPS ensures data is encrypted in transit between your browser and the web server, preventing man-in-the-middle eavesdropping."),
        LearningArticle("3", "Decoding URL Structure", "Advanced", "Breaking down subdomains, domains, and paths.", "Always inspect the root domain before trusting a link. Subdomains can be spoofed easily (e.g. login.secure-bank.com.attacker.xyz)."),
        LearningArticle("4", "Spotting Fake Websites", "Safety", "Visual and behavioral clues of fraudulent portals.", "Look out for low-resolution logos, grammatical errors, and pressure tactics.")
    )

    var selectedArticle by remember { mutableStateOf<LearningArticle?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Learning Center") },
                navigationIcon = {
                    IconButton(onClick = { if (selectedArticle != null) selectedArticle = null else onBack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (selectedArticle == null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(articles) { article ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedArticle = article },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = article.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Surface(shape = MaterialTheme.shapes.small, color = MaterialTheme.colorScheme.primaryContainer) {
                                    Text(text = article.category, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = article.summary, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = selectedArticle!!.title, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = MaterialTheme.colorScheme.primary)
                Text(text = selectedArticle!!.content, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LearningCenterScreenPreview() {
    LinkSaveAiTheme {
        LearningCenterScreen(onBack = {})
    }
}
