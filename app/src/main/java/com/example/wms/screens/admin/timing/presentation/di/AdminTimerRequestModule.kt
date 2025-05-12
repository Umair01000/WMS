package com.example.wms.screens.admin.timing.presentation.di

import com.example.wms.screens.admin.timing.presentation.AdminTimerViewModel
import com.example.wms.screens.admin.timing.data.repository.WorkingHoursRepositoryImpl
import com.example.wms.screens.admin.timing.domain.repository.WorkingHoursRepository
import com.example.wms.screens.admin.timing.domain.usecase.GetWorkingHoursUseCase
import com.example.wms.screens.admin.timing.domain.usecase.SaveWorkingHoursUseCase
import com.google.firebase.firestore.FirebaseFirestore

object AdminTimerRequestModule {

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val workingHoursRepository: WorkingHoursRepository by lazy {
        WorkingHoursRepositoryImpl(firestore)
    }

    private val saveWorkingHoursUseCase: SaveWorkingHoursUseCase by lazy {
        SaveWorkingHoursUseCase(workingHoursRepository)
    }

    private val getWorkingHoursUseCase: GetWorkingHoursUseCase by lazy {
        GetWorkingHoursUseCase(workingHoursRepository)
    }

    // Provide the ViewModel instance
    fun provideViewModel(): AdminTimerViewModel {
        return AdminTimerViewModel(saveWorkingHoursUseCase, getWorkingHoursUseCase)
    }
}
