package com.example.wms.screens.user.chatbot.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wms.screens.user.chatbot.domain.model.ContentRequest
import com.example.wms.screens.user.chatbot.domain.model.GeminiResponse
import com.example.wms.screens.user.chatbot.domain.model.PartRequest
import com.example.wms.screens.user.chatbot.domain.model.QueryRequest
import com.example.wms.screens.user.chatbot.domain.model.UsageMetadata
import com.example.wms.screens.user.chatbot.domain.usecase.GetGeminiResponseUseCase
import kotlinx.coroutines.launch

class GeminiViewModel(private val getGeminiResponseUseCase: GetGeminiResponseUseCase) :
    ViewModel() {

    private val _geminiResponse = MutableLiveData<GeminiResponse>()
    val geminiResponse: LiveData<GeminiResponse> get() = _geminiResponse

    fun getGeminiResponse(queryRequest: QueryRequest) {
        viewModelScope.launch {
            try {
                // Modify query to strictly answer about WASA Rawalpindi
                val modifiedQueryRequest = appendWasaContext(queryRequest)
                val response = getGeminiResponseUseCase.execute(modifiedQueryRequest)
                _geminiResponse.postValue(response)
            } catch (exception: Exception) {
                _geminiResponse.postValue(
                    GeminiResponse(
                        emptyList(), UsageMetadata(0, 0, 0, emptyList(), emptyList()), ""
                    )
                )
            }
        }
    }

    private fun appendWasaContext(queryRequest: QueryRequest): QueryRequest {
        val modifiedContents = queryRequest.contents.map { contentRequest ->
            val modifiedParts = contentRequest.parts.map { partRequest ->
                val modifiedText = """
                    Answer the following question strictly about WASA Rawalpindi: 
                    ${partRequest.text} 
                """.trimIndent()
                PartRequest(modifiedText)
            }
            ContentRequest(modifiedParts)
        }
        return QueryRequest(modifiedContents)
    }
}
