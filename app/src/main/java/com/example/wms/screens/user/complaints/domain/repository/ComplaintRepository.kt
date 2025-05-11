package com.example.wms.screens.user.complaints.domain.repository

import com.example.wms.screens.user.complaints.domain.model.ComplaintData

sealed class ComplaintResult {
    object Loading : ComplaintResult()
    data class Success(val message: String) : ComplaintResult()
    data class Error(val message: String) : ComplaintResult()
}

interface ComplaintRepository {
    suspend fun saveComplaint(complaintData: ComplaintData): ComplaintResult
}
