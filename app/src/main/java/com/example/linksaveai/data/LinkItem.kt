package com.example.linksaveai.data

data class LinkItem(
    val id: String,
    val url: String,
    val domain: String,
    val status: String, // "Safe", "Suspicious", "DEMO"
    val timestamp: String,
    val isSaved: Boolean = false,
    val details: String = "Scanned using LinkSafe AI heuristics engine."
)
