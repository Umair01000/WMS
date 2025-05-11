package com.example.wms.screens.user.apply.domain.repository

import com.example.wms.screens.user.apply.domain.model.ApplyData
sealed class ApplyResult {
    object Loading : ApplyResult()
    data class Success(val message: String) : ApplyResult()
    data class Error(val message: String) : ApplyResult()
}
interface ApplyRepository {
    suspend fun saveApplication(applyData: ApplyData): ApplyResult
}
