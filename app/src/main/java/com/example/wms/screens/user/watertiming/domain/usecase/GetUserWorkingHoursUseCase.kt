package com.example.wms.screens.user.watertiming.domain.usecase

import com.example.wms.screens.user.watertiming.domain.model.UserWorkingHours
import com.example.wms.screens.user.watertiming.domain.repository.UserWorkingHoursRepository

class GetUserWorkingHoursUseCase(
    private val userWorkingHoursRepository: UserWorkingHoursRepository
) {
    suspend fun execute(): UserWorkingHours? {
        return userWorkingHoursRepository.getUserWorkingHours()
    }
}
