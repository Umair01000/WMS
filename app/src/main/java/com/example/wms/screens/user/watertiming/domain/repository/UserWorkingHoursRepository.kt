package com.example.wms.screens.user.watertiming.domain.repository

import com.example.wms.screens.user.watertiming.domain.model.UserWorkingHours

interface UserWorkingHoursRepository {
    suspend fun getUserWorkingHours(): UserWorkingHours?
}
