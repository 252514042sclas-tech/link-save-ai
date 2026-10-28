package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
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
fun ReportLinkScreen(
    onBack: () -> Unit,
    onSubmitReport: (String, String) -> Unit
) {
    var urlInput by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("Phishing") }
    var description by remember { mutableStateOf("") }
    var submitted by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Report Suspicious Link") },
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
            if (submitted) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Report Submitted Successfully!", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Thank you for helping keep the LinkSafe AI community secure.")
                    }
                }
            }

            Text(text = "Help protect others by reporting fraudulent or malicious URLs.", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

            OutlinedTextField(
                value = urlInput,
                onValueChange = { urlInput = it },
                label = { Text("Suspicious URL") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Text(text = "Reason for Report", fontWeight = FontWeight.Bold)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = reason == "Phishing", onClick = { reason = "Phishing" }, label = { Text("Phishing") })
                FilterChip(selected = reason == "Malware", onClick = { reason = "Malware" }, label = { Text("Malware") })
                FilterChip(selected = reason == "Scam", onClick = { reason = "Scam" }, label = { Text("Scam") })
            }

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Additional Description (Optional)") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 4
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (urlInput.isNotBlank()) {
                        onSubmitReport(urlInput, reason)
                        submitted = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = urlInput.isNotBlank() && !submitted
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Submit Report")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportLinkScreenPreview() {
    LinkSaveAiTheme {
        ReportLinkScreen(onBack = {}, onSubmitReport = { _, _ -> })
    }
}
