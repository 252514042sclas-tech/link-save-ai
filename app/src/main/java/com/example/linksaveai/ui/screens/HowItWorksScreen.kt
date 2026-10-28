package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linksaveai.ui.components.StepCard
import com.example.linksaveai.ui.theme.LinkSaveAiTheme

@Composable
fun HowItWorksScreen(onNext: () -> Unit = {}) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "How LinkSafe AI Works",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Three simple steps to protect you from malicious web links.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(32.dp))

                StepCard(
                    stepNumber = "1",
                    title = "Detect URL",
                    description = "Enter or paste any suspicious web link directly into the scanner box or share via browser.",
                    icon = Icons.Default.Search
                )
                Spacer(modifier = Modifier.height(16.dp))
                StepCard(
                    stepNumber = "2",
                    title = "Analyze URL",
                    description = "Our heuristics engine checks domain reputation, SSL status, and recognized phishing patterns.",
                    icon = Icons.Default.Shield
                )
                Spacer(modifier = Modifier.height(16.dp))
                StepCard(
                    stepNumber = "3",
                    title = "Show Safety Result",
                    description = "Receive immediate Safe or Suspicious results with actionable safety advice and security tips.",
                    icon = Icons.Default.CheckCircle
                )
            }

            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Continue", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HowItWorksScreenPreview() {
    LinkSaveAiTheme {
        HowItWorksScreen()
    }
}
