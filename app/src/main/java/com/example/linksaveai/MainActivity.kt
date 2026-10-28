package com.example.linksaveai

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.linksaveai.navigation.LinkSafeNavGraph
import com.example.linksaveai.service.SharedLinkHolder
import com.example.linksaveai.ui.theme.LinkSaveAiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        handleSharedIntent(intent)
        setContent {
            LinkSaveAiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    LinkSafeNavGraph()
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleSharedIntent(intent)
    }

    private fun handleSharedIntent(intent: Intent) {
        if (intent.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            intent.getStringExtra(Intent.EXTRA_TEXT)?.let { sharedText ->
                val url = extractUrl(sharedText) ?: sharedText
                SharedLinkHolder.onUrlCaptured(url, "Sharesheet")
            }
        }
    }

    private fun extractUrl(text: String): String? {
        val pattern = android.util.Patterns.WEB_URL
        val matcher = pattern.matcher(text)
        return if (matcher.find()) matcher.group() else null
    }
}
