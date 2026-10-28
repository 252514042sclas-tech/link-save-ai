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
import com.example.linksaveai.ui.theme.LinkSaveAiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutLinkSafeScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About LinkSafe AI") },
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Security,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Text(text = "LinkSafe AI", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Text(text = "Version 1.0.0 (Build 30)", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Project Purpose", fontWeight = FontWeight.Bold)
                    Text(text = "LinkSafe AI provides advanced heuristic URL inspection to protect users from phishing, malware, and fraudulent online scams.")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Disclaimer", fontWeight = FontWeight.Bold)
                    Text(text = "Scan results are generated using local heuristics and demo analysis. Results may occasionally be inaccurate. Exercise caution when clicking unknown links.")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutLinkSafeScreenPreview() {
    LinkSaveAiTheme {
        AboutLinkSafeScreen(onBack = {})
    }
}
