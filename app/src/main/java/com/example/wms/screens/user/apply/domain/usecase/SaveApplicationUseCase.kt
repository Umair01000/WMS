package com.example.wms.screens.user.apply.domain.usecase

import com.example.wms.screens.user.apply.domain.model.ApplyData
import com.example.wms.screens.user.apply.domain.repository.ApplyRepository
import com.example.wms.screens.user.apply.domain.repository.ApplyResult

class SaveApplicationUseCase(private val applyRepository: ApplyRepository) {

    suspend fun execute(applyData: ApplyData): ApplyResult =
        applyRepository.saveApplication(applyData)
}
