package com.example.wms.screens.user.complaints.domain.usecase

import com.example.wms.screens.user.complaints.domain.model.ComplaintData
import com.example.wms.screens.user.complaints.domain.repository.ComplaintRepository
import com.example.wms.screens.user.complaints.domain.repository.ComplaintResult

class SaveComplaintUseCase(private val complaintRepository: ComplaintRepository) {

    suspend fun execute(complaintData: ComplaintData): ComplaintResult =
        complaintRepository.saveComplaint(complaintData)
}
