package com.example.wms.screens.user.chatbot.presentation.di

import com.example.wms.screens.user.chatbot.data.remote.GeminiApiService
import com.example.wms.screens.user.chatbot.data.repository.GeminiRepositoryImpl
import com.example.wms.screens.user.chatbot.domain.repository.GeminiRepository
import com.example.wms.screens.user.chatbot.domain.usecase.GetGeminiResponseUseCase
import com.example.wms.screens.user.chatbot.presentation.GeminiViewModel

object ChatbotModule {
    private val apiService: GeminiApiService by lazy {
        RetrofitClient.apiService
    }
    private val repository: GeminiRepository by lazy {
        GeminiRepositoryImpl(apiService)
    }
    private val getChatbotResponseUseCase: GetGeminiResponseUseCase by lazy {
        GetGeminiResponseUseCase(repository)
    }

    fun provideViewModel(): GeminiViewModel {
        return GeminiViewModel(getChatbotResponseUseCase)
    }
}