package com.example.wms.screens.admin.timing.domain.repository

import com.example.wms.screens.admin.timing.domain.model.WorkingHours

interface WorkingHoursRepository {
    suspend fun saveWorkingHours(workingHours: WorkingHours): Boolean
    suspend fun getWorkingHours(): WorkingHours?
}
