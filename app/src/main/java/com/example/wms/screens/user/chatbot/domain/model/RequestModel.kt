package com.example.wms.screens.user.chatbot.domain.model

data class QueryRequest(
    val contents: List<ContentRequest>
)

data class ContentRequest(
    val parts: List<PartRequest>
)

data class PartRequest(
    val text: String
)
