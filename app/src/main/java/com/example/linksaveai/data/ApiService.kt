package com.example.linksaveai.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("analyze")
    suspend fun analyzeUrl(@Body request: AnalysisRequest): Response<AnalysisResponse>
}
