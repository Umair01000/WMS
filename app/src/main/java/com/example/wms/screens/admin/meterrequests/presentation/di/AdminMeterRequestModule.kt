package com.example.wms.screens.admin.meterrequests.presentation.di

import com.example.wms.screens.admin.meterrequests.data.repository.AdminMeterRequestRepositoryImpl
import com.example.wms.screens.admin.meterrequests.domain.repository.AdminMeterRequestRepository
import com.example.wms.screens.admin.meterrequests.domain.usecase.GetMeterApplicationsUseCase
import com.example.wms.screens.admin.meterrequests.presentation.AdminMeterRequestViewModel
import com.google.firebase.firestore.FirebaseFirestore

object AdminMeterRequestModule {
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val adminMeterRequestRepository: AdminMeterRequestRepository by lazy {
        AdminMeterRequestRepositoryImpl(firestore)
    }

    private val getMeterApplicationsUseCase: GetMeterApplicationsUseCase by lazy {
        GetMeterApplicationsUseCase(adminMeterRequestRepository)
    }

    fun provideViewModel(): AdminMeterRequestViewModel {
        return AdminMeterRequestViewModel(getMeterApplicationsUseCase)
    }
}
