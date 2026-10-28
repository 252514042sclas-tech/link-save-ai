package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SecurityTip(val title: String, val description: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityTipsScreen(onBack: () -> Unit) {
    val tips = listOf(
        SecurityTip(
            title = "Beware of Phishing Emails",
            description = "Scammers often spoof official banks or services. Always check the exact sender address and domain before clicking links.",
            icon = Icons.Default.Warning
        ),
        SecurityTip(
            title = "Fake Websites & Lookalikes",
            description = "Check spelling carefully (e.g., paypol.com instead of paypal.com). Attackers use minor typos to deceive users.",
            icon = Icons.Default.Warning
        ),
        SecurityTip(
            title = "Suspicious SMS Messages",
            description = "Never click links in unexpected text messages about package deliveries or prize wins without independent verification.",
            icon = Icons.Default.Security
        ),
        SecurityTip(
            title = "Risks of Shortened URLs",
            description = "URL shorteners hide the final destination. Use LinkSafe AI preview to inspect where short links redirect.",
            icon = Icons.Default.Security
        ),
        SecurityTip(
            title = "Strong Password Protection",
            description = "Use unique passwords and multi-factor authentication (MFA) across all your online accounts to prevent unauthorized access.",
            icon = Icons.Default.Lock
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Security Tips") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tips) { tip ->
                TipCard(tip = tip)
            }
        }
    }
}

@Composable
fun TipCard(tip: SecurityTip) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = tip.icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = tip.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = tip.description, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
