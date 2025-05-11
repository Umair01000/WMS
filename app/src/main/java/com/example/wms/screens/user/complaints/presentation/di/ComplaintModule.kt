package com.example.wms.screens.user.complaints.presentation.di

import com.example.wms.screens.user.complaints.data.repository.ComplaintRepositoryImpl
import com.example.wms.screens.user.complaints.domain.repository.ComplaintRepository
import com.example.wms.screens.user.complaints.domain.usecase.SaveComplaintUseCase
import com.example.wms.screens.user.complaints.presentation.ComplaintViewModel
import com.google.firebase.firestore.FirebaseFirestore

object ComplaintModule {

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val repository: ComplaintRepository by lazy {
        ComplaintRepositoryImpl(firestore)
    }

    private val saveComplaintUseCase: SaveComplaintUseCase by lazy {
        SaveComplaintUseCase(repository)
    }

    fun provideViewModel(): ComplaintViewModel {
        return ComplaintViewModel(saveComplaintUseCase)
    }
}
