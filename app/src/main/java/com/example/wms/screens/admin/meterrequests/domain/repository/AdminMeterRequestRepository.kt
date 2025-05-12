package com.example.wms.screens.admin.meterrequests.domain.repository

import com.example.wms.screens.admin.meterrequests.domain.model.MeterApplication

interface AdminMeterRequestRepository {
    suspend fun getMeterApplications(): List<MeterApplication>
}
