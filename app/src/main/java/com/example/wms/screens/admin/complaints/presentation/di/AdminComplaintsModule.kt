package com.example.wms.screens.admin.complaints.presentation.di

import com.example.wms.screens.admin.complaints.data.repository.AdminComplaintsRepositoryImpl
import com.example.wms.screens.admin.complaints.domain.repository.AdminComplaintsRepository
import com.example.wms.screens.admin.complaints.domain.usecase.GetUserComplaintsUseCase
import com.example.wms.screens.admin.complaints.presentation.AdminComplaintsViewModel
import com.google.firebase.firestore.FirebaseFirestore

object AdminComplaintsModule {
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val adminComplaintsRepository: AdminComplaintsRepository by lazy {
        AdminComplaintsRepositoryImpl(firestore)
    }

    private val getUserComplaintsUseCase: GetUserComplaintsUseCase by lazy {
        GetUserComplaintsUseCase(adminComplaintsRepository)
    }

    fun provideViewModel(): AdminComplaintsViewModel {
        return AdminComplaintsViewModel(getUserComplaintsUseCase)
    }
}
