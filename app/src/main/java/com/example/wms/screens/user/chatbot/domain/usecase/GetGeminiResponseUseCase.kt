package com.example.wms.screens.user.chatbot.domain.usecase

import com.example.wms.screens.user.chatbot.domain.model.GeminiResponse
import com.example.wms.screens.user.chatbot.domain.model.QueryRequest
import com.example.wms.screens.user.chatbot.domain.repository.GeminiRepository

class GetGeminiResponseUseCase(private val geminiRepository: GeminiRepository) {

    suspend fun execute(queryRequest: QueryRequest): GeminiResponse {
        return geminiRepository.getResponseFromGemini(queryRequest)
    }
}
