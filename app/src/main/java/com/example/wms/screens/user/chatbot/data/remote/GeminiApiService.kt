package com.example.wms.screens.user.chatbot.data.remote

import com.example.wms.screens.user.chatbot.domain.model.GeminiResponse
import com.example.wms.screens.user.chatbot.domain.model.QueryRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiApiService {

    @POST("v1beta/models/gemini-1.5-flash-latest:generateContent")
    suspend fun getResponse(
        @Body queryRequest: QueryRequest,
        @Query("key") apiKey: String // Add the key query parameter here
    ): Response<GeminiResponse>
}
