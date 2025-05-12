package com.example.wms.screens.user.chatbot.domain.repository

import com.example.wms.screens.user.chatbot.domain.model.GeminiResponse
import com.example.wms.screens.user.chatbot.domain.model.QueryRequest

interface GeminiRepository {
    suspend fun getResponseFromGemini(queryRequest: QueryRequest): GeminiResponse
}
