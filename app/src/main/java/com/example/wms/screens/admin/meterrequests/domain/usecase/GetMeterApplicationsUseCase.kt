package com.example.wms.screens.admin.meterrequests.domain.usecase

import com.example.wms.screens.admin.meterrequests.domain.model.MeterApplication
import com.example.wms.screens.admin.meterrequests.domain.repository.AdminMeterRequestRepository

class GetMeterApplicationsUseCase(
    private val adminMeterRequestRepository: AdminMeterRequestRepository
) {

    suspend fun execute(): List<MeterApplication> {
        return adminMeterRequestRepository.getMeterApplications()
    }
}
