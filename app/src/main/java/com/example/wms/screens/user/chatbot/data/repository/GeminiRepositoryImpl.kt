package com.example.wms.screens.user.chatbot.data.repository

import com.example.wms.screens.user.chatbot.data.remote.GeminiApiService
import com.example.wms.screens.user.chatbot.domain.model.GeminiResponse
import com.example.wms.screens.user.chatbot.domain.model.QueryRequest
import com.example.wms.screens.user.chatbot.domain.model.UsageMetadata
import com.example.wms.screens.user.chatbot.domain.repository.GeminiRepository

private const val API_KEY =
    "AIzaSyAKZXMA6zK2V88J5kjBlW5MnU2ebWzk70Q"

class GeminiRepositoryImpl(private val apiService: GeminiApiService) : GeminiRepository {

    override suspend fun getResponseFromGemini(queryRequest: QueryRequest): GeminiResponse {
        val response = apiService.getResponse(queryRequest, API_KEY)
        return response.body() ?: GeminiResponse(
            emptyList(),
            UsageMetadata(0, 0, 0, emptyList(), emptyList()),
            ""
        )
    }
}
