package com.example.linksaveai.data

data class AnalysisRequest(val url: String)

data class AnalysisResponse(
    val url: String,
    val status: String,
    val confidence: Float,
    val explanation: String
)
