package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityTipsDetailsScreen(
    tipTitle: String = "Phishing Links & Fake Login Pages",
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Security Tip Details") },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = tipTitle, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = MaterialTheme.colorScheme.primary)

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Overview", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = "Cybercriminals frequently use replica domains (typosquatting) and urgent messaging to trick users into entering credentials on fake login portals.")
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Key Indicators to Watch:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = "• Mismatched domain names (e.g. g00gle.com instead of google.com)")
                    Text(text = "• Unencrypted HTTP connection in sensitive areas")
                    Text(text = "• Artificial urgency ('Account suspended in 24 hours')")
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Best Practices:", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = "1. Always bookmark official banking and login sites.")
                    Text(text = "2. Run URLs through LinkSafe AI prior to signing in.")
                    Text(text = "3. Never share OTPs or passwords over SMS or unverified links.")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecurityTipsDetailsScreenPreview() {
    LinkSaveAiTheme {
        SecurityTipsDetailsScreen(onBack = {})
    }
}
