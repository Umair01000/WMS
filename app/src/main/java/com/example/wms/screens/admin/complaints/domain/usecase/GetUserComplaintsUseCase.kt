package com.example.wms.screens.admin.complaints.domain.usecase

import com.example.wms.screens.admin.complaints.domain.model.UserComplaint
import com.example.wms.screens.admin.complaints.domain.repository.AdminComplaintsRepository

class GetUserComplaintsUseCase(
    private val adminComplaintsRepository: AdminComplaintsRepository
) {

    suspend fun execute(): List<UserComplaint> {
        return adminComplaintsRepository.getUserComplaints()
    }
}