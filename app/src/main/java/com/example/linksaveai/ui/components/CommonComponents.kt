package com.example.linksaveai.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linksaveai.data.LinkItem
import com.example.linksaveai.navigation.Screen

@Composable
fun StatCard(
    title: String,
    count: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = count, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = contentColor)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = contentColor)
        }
    }
}

@Composable
fun HistoryItemCard(item: LinkItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (item.status == "Safe") Icons.Default.CheckCircle else Icons.Default.Warning,
                contentDescription = null,
                tint = if (item.status == "Safe") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.domain, fontWeight = FontWeight.Bold, fontSize = 15.sp, maxLines = 1)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = item.timestamp, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Surface(
                shape = MaterialTheme.shapes.small,
                color = if (item.status == "Safe") MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer
            ) {
                Text(
                    text = item.status,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (item.status == "Safe") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun StepCard(stepNumber: String, title: String, description: String, icon: ImageVector? = null) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (icon != null) {
                        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(20.dp))
                    } else {
                        Text(text = stepNumber, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = description, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
fun LinkSafeBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = { onNavigate(Screen.Home.route) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Scanner.route,
            onClick = { onNavigate(Screen.Scanner.route) },
            icon = { Icon(Icons.Default.Search, contentDescription = "Scan") },
            label = { Text("Scan") }
        )
        NavigationBarItem(
            selected = currentRoute == Screen.SavedLinks.route,
            onClick = { onNavigate(Screen.SavedLinks.route) },
            icon = { Icon(Icons.Default.Bookmark, contentDescription = "Saved") },
            label = { Text("Saved") }
        )
        NavigationBarItem(
            selected = currentRoute == Screen.ScanHistory.route,
            onClick = { onNavigate(Screen.ScanHistory.route) },
            icon = { Icon(Icons.Default.History, contentDescription = "Activity") },
            label = { Text("Activity") }
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Settings.route,
            onClick = { onNavigate(Screen.Settings.route) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") }
        )
    }
}
