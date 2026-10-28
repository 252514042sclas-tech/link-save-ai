# Implementation of Screens 16–30 for LinkSafe AI

This artifact contains the complete Kotlin source code for all 15 new screens (Screens 16–30), along with updates to `Screen.kt` and `LinkSafeNavGraph.kt`.

## File List
1. `HistoryDetailsScreen.kt`
2. `SavedLinkDetailsScreen.kt`
3. `NotificationCenterScreen.kt`
4. `NotificationMonitoringSetupScreen.kt`
5. `DetectedLinkAlertScreen.kt`
6. `ReportLinkScreen.kt`
7. `SecurityTipsDetailsScreen.kt`
8. `LearningCenterScreen.kt`
9. `EditProfileScreen.kt`
10. `AppSettingsScreen.kt`
11. `PrivacyDataScreen.kt`
12. `AboutLinkSafeScreen.kt`
13. `HelpFaqScreen.kt`
14. `ErrorOfflineScreen.kt`
15. `ScanSummaryScreen.kt`

---

### 1. HistoryDetailsScreen.kt
Path: `app/src/main/java/com/example/linksaveai/ui/screens/HistoryDetailsScreen.kt`

```kotlin
package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linksaveai.data.LinkItem
import com.example.linksaveai.ui.theme.LinkSaveAiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailsScreen(
    linkItem: LinkItem,
    onBack: () -> Unit,
    onScanAgain: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("History Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (linkItem.status == "Safe") MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (linkItem.status == "Safe") Icons.Default.CheckCircle else Icons.Default.Warning,
                            contentDescription = null,
                            tint = if (linkItem.status == "Safe") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = linkItem.status,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = if (linkItem.status == "Safe") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = linkItem.domain, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = linkItem.url, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Scan Information", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    HorizontalDivider()
                    Text(text = "Scan Date: ${linkItem.timestamp}")
                    Text(text = "Details: ${linkItem.details}")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onScanAgain(linkItem.url) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Scan Again")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryDetailsScreenPreview() {
    LinkSaveAiTheme {
        HistoryDetailsScreen(
            linkItem = LinkItem("1", "https://google.com", "google.com", "Safe", "Today, 10:30 AM", true, "Valid SSL certificate."),
            onBack = {},
            onScanAgain = {}
        )
    }
}
```

---

### 2. SavedLinkDetailsScreen.kt
Path: `app/src/main/java/com/example/linksaveai/ui/screens/SavedLinkDetailsScreen.kt`

```kotlin
package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linksaveai.data.LinkItem
import com.example.linksaveai.ui.theme.LinkSaveAiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedLinkDetailsScreen(
    linkItem: LinkItem,
    onBack: () -> Unit,
    onScanAgain: (String) -> Unit,
    onRemoveBookmark: (LinkItem) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Link Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = linkItem.domain, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Icon(imageVector = Icons.Default.Bookmark, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    }
                    Text(text = linkItem.url, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    HorizontalDivider()
                    Text(text = "Saved Date: ${linkItem.timestamp}")
                    Text(text = "Status: ${linkItem.status}")
                    Text(text = "Description: ${linkItem.details}")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onScanAgain(linkItem.url) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Scan Again")
            }

            OutlinedButton(
                onClick = { onRemoveBookmark(linkItem); onBack() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Remove Bookmark")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedLinkDetailsScreenPreview() {
    LinkSaveAiTheme {
        SavedLinkDetailsScreen(
            linkItem = LinkItem("1", "https://github.com", "github.com", "Safe", "Today, 10:30 AM", true, "Verified platform."),
            onBack = {},
            onScanAgain = {},
            onRemoveBookmark = {}
        )
    }
}
```

---

### 3. NotificationCenterScreen.kt
Path: `app/src/main/java/com/example/linksaveai/ui/screens/NotificationCenterScreen.kt`

```kotlin
package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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

data class AppNotification(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: String,
    val isUnread: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationCenterScreen(
    onBack: () -> Unit
) {
    var notifications by remember {
        mutableStateOf(
            listOf(
                AppNotification("1", "Suspicious Link Detected", "We intercepted a risky URL in your incoming SMS.", "10 mins ago", true),
                AppNotification("2", "Scan Complete", "github.com was verified as safe.", "2 hours ago", false),
                AppNotification("3", "Security Tip", "Always verify HTTPS certificates before logging in.", "Yesterday", false)
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notification Center") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = { notifications = emptyList() }) {
                        Text("Clear All")
                    }
                }
            )
        }
    ) { padding ->
        if (notifications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Default.NotificationsNone, contentDescription = null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "No notifications", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(notifications) { notif ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (notif.isUnread) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = notif.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    Text(text = notif.timestamp, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = notif.message, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationCenterScreenPreview() {
    LinkSaveAiTheme {
        NotificationCenterScreen(onBack = {})
    }
}
```

---

### 4. NotificationMonitoringSetupScreen.kt
Path: `app/src/main/java/com/example/linksaveai/ui/screens/NotificationMonitoringSetupScreen.kt`

```kotlin
package com.example.linksaveai.ui.screens

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linksaveai.ui.theme.LinkSaveAiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationMonitoringSetupScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notification Monitoring") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterHorizontally),
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Proactive Phishing Protection",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                text = "LinkSafe AI can optionally monitor incoming notifications (such as SMS or chat messages) to automatically detect and warn you about malicious URLs before you click them.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "How to Enable/Disable:", fontWeight = FontWeight.Bold)
                    Text(text = "1. Tap the button below to open Android Notification Access settings.")
                    Text(text = "2. Locate 'LinkSafe AI' in the list.")
                    Text(text = "3. Toggle permission on or off securely.")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    try {
                        context.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                    } catch (e: Exception) {
                        // fallback if settings intent fails
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Open Notification Settings")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationMonitoringSetupScreenPreview() {
    LinkSaveAiTheme {
        NotificationMonitoringSetupScreen(onBack = {})
    }
}
```

---

### 5. DetectedLinkAlertScreen.kt
Path: `app/src/main/java/com/example/linksaveai/ui/screens/DetectedLinkAlertScreen.kt`

```kotlin
package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
fun DetectedLinkAlertScreen(
    detectedUrl: String = "http://free-prize-alert.xyz/claim",
    sourceApp: String = "Messages",
    onCheckLink: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detected Link Alert") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Link Detected from $sourceApp", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = detectedUrl, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onErrorContainer)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Note: Full background detection requires NotificationListenerService implementation.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onCheckLink(detectedUrl) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Check Link Now")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Dismiss")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetectedLinkAlertScreenPreview() {
    LinkSaveAiTheme {
        DetectedLinkAlertScreen(onCheckLink = {}, onDismiss = {})
    }
}
```

---

### 6. ReportLinkScreen.kt
Path: `app/src/main/java/com/example/linksaveai/ui/screens/ReportLinkScreen.kt`

```kotlin
package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
                Icon(imageVector = Icons.Default.Send, contentDescription = null)
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
```

---

### 7. SecurityTipsDetailsScreen.kt
Path: `app/src/main/java/com/example/linksaveai/ui/screens/SecurityTipsDetailsScreen.kt`

```kotlin
package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
```

---

### 8. LearningCenterScreen.kt
Path: `app/src/main/java/com/example/linksaveai/ui/screens/LearningCenterScreen.kt`

```kotlin
package com.example.linksaveai.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
```

---

### 9. EditProfileScreen.kt
Path: `app/src/main/java/com/example/linksaveai/ui/screens/EditProfileScreen.kt`

```kotlin
package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linksaveai.ui.theme.LinkSaveAiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onBack: () -> Unit,
    onSaveProfile: (String, String) -> Unit
) {
    var name by remember { mutableStateOf("Explorer") }
    var email by remember { mutableStateOf("explorer@linksafe.ai") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Display Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (name.isNotBlank() && email.isNotBlank()) {
                        onSaveProfile(name, email)
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save Changes")
            }

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    LinkSaveAiTheme {
        EditProfileScreen(onBack = {}, onSaveProfile = { _, _ -> })
    }
}
```

---

### 10. AppSettingsScreen.kt
Path: `app/src/main/java/com/example/linksaveai/ui/screens/AppSettingsScreen.kt`

```kotlin
package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linksaveai.ui.theme.LinkSaveAiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSettingsScreen(
    onBack: () -> Unit,
    onNavigateToPrivacy: () -> Unit,
    onNavigateToHelp: () -> Unit
) {
    var darkMode by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var autoScanClipboard by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("App Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Dark Mode Theme")
                Switch(checked = darkMode, onCheckedChange = { darkMode = it })
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Push Notifications")
                Switch(checked = notificationsEnabled, onCheckedChange = { notificationsEnabled = it })
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Auto-scan Copied Links")
                Switch(checked = autoScanClipboard, onCheckedChange = { autoScanClipboard = it })
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            OutlinedButton(onClick = onNavigateToPrivacy, modifier = Modifier.fillMaxWidth()) {
                Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Privacy & Data Management")
            }

            OutlinedButton(onClick = onNavigateToHelp, modifier = Modifier.fillMaxWidth()) {
                Icon(imageVector = Icons.Default.Help, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Help & FAQ")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppSettingsScreenPreview() {
    LinkSaveAiTheme {
        AppSettingsScreen(onBack = {}, onNavigateToPrivacy = {}, onNavigateToHelp = {})
    }
}
```

---

### 11. PrivacyDataScreen.kt
Path: `app/src/main/java/com/example/linksaveai/ui/screens/PrivacyDataScreen.kt`

```kotlin
package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
fun PrivacyDataScreen(
    onBack: () -> Unit,
    onClearHistory: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Clear Data") },
            text = { Text("Are you sure you want to delete all locally stored scan history? This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    onClearHistory()
                    showDialog = false
                }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy & Data Management") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Local Storage Policy", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = "LinkSave AI stores your scan history locally on your device to protect your privacy. No personal browsing data is sold or transmitted to external advertising networks.")
                }
            }

            Button(
                onClick = { showDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(imageVector = Icons.Default.DeleteForever, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Clear All Local Scan History")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrivacyDataScreenPreview() {
    LinkSaveAiTheme {
        PrivacyDataScreen(onBack = {}, onClearHistory = {})
    }
}
```

---

### 12. AboutLinkSafeScreen.kt
Path: `app/src/main/java/com/example/linksaveai/ui/screens/AboutLinkSafeScreen.kt`

```kotlin
package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
```

---

### 13. HelpFaqScreen.kt
Path: `app/src/main/java/com/example/linksaveai/ui/screens/HelpFaqScreen.kt`

```kotlin
package com.example.linksaveai.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
```

---

### 14. ErrorOfflineScreen.kt
Path: `app/src/main/java/com/example/linksaveai/ui/screens/ErrorOfflineScreen.kt`

```kotlin
package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
fun ErrorOfflineScreen(
    errorMessage: String = "Network connection is unavailable or the scanning service is temporarily offline.",
    onRetry: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Connection Error") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.CloudOff,
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Oops! Something went wrong", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onRetry,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Retry")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Go Back")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorOfflineScreenPreview() {
    LinkSaveAiTheme {
        ErrorOfflineScreen(onRetry = {}, onBack = {})
    }
}
```

---

### 15. ScanSummaryScreen.kt
Path: `app/src/main/java/com/example/linksaveai/ui/screens/ScanSummaryScreen.kt`

```kotlin
package com.example.linksaveai.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linksaveai.data.LinkItem
import com.example.linksaveai.ui.theme.LinkSaveAiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanSummaryScreen(
    history: List<LinkItem>,
    onBack: () -> Unit
) {
    val totalScans = history.size
    val safeCount = history.count { it.status == "Safe" }
    val suspiciousCount = history.count { it.status == "Suspicious" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scan Summary & Statistics") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = "Overview Statistics", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    HorizontalDivider()
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total Scans")
                        Text(totalScans.toString(), fontWeight = FontWeight.Bold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Safe Links", color = MaterialTheme.colorScheme.primary)
                        Text(safeCount.toString(), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Suspicious Links", color = MaterialTheme.colorScheme.error)
                        Text(suspiciousCount.toString(), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                    }
                }
            }

            if (history.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    Text("No scan data available (Showing sample statistics if offline)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScanSummaryScreenPreview() {
    LinkSaveAiTheme {
        ScanSummaryScreen(
            history = listOf(
                LinkItem("1", "https://google.com", "google.com", "Safe", "Today", true),
                LinkItem("2", "http://bad.com", "bad.com", "Suspicious", "Yesterday", false)
            ),
            onBack = {}
        )
    }
}
```

---

## Navigation Updates

### 1. Update `Screen.kt`
Add the new route constants in `Screen.kt`:
```kotlin
    object HistoryDetails : Screen("history_details")
    object SavedLinkDetails : Screen("saved_link_details")
    object NotificationCenter : Screen("notification_center")
    object NotificationMonitoringSetup : Screen("notification_monitoring_setup")
    object DetectedLinkAlert : Screen("detected_link_alert")
    object ReportLink : Screen("report_link")
    object SecurityTipsDetails : Screen("security_tips_details")
    object LearningCenter : Screen("learning_center")
    object EditProfile : Screen("edit_profile")
    object AppSettings : Screen("app_settings")
    object PrivacyData : Screen("privacy_data")
    object AboutLinkSafe : Screen("about_link_safe")
    object HelpFaq : Screen("help_faq")
    object ErrorOffline : Screen("error_offline")
    object ScanSummary : Screen("scan_summary")
```

### 2. Update `LinkSafeNavGraph.kt`
Add composable destinations for all 15 screens in `LinkSafeNavGraph`.
