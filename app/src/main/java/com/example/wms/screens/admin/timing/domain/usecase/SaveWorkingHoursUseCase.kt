package com.example.wms.screens.admin.timing.domain.usecase

import com.example.wms.screens.admin.timing.domain.model.WorkingHours
import com.example.wms.screens.admin.timing.domain.repository.WorkingHoursRepository

class SaveWorkingHoursUseCase(
    private val workingHoursRepository: WorkingHoursRepository
) {
    suspend fun execute(workingHours: WorkingHours): Boolean {
        return workingHoursRepository.saveWorkingHours(workingHours)
    }
}

class GetWorkingHoursUseCase(
    private val workingHoursRepository: WorkingHoursRepository
) {
    suspend fun execute(): WorkingHours? {
        return workingHoursRepository.getWorkingHours()
    }
}
