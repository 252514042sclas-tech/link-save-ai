package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linksaveai.ui.theme.LinkSaveAiTheme
import kotlinx.coroutines.delay

@Composable
fun ScanningProgressScreen(
    url: String = "https://example.com/secure-login",
    onScanFinished: () -> Unit = {}
) {
    var stepIndex by remember { mutableStateOf(0) }
    val steps = listOf(
        "Checking URL syntax & format...",
        "Querying domain reputation & SSL certificate...",
        "Analyzing heuristic patterns & phishing signatures..."
    )

    LaunchedEffect(key1 = true) {
        delay(800L)
        stepIndex = 1
        delay(900L)
        stepIndex = 2
        delay(800L)
        onScanFinished()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(80.dp),
                strokeWidth = 6.dp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Scanning URL...",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = url,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(32.dp))

            steps.forEachIndexed { index, text ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = index <= stepIndex,
                        onClick = { }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = text,
                        fontSize = 14.sp,
                        color = if (index <= stepIndex) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScanningProgressScreenPreview() {
    LinkSaveAiTheme {
        ScanningProgressScreen()
    }
}
