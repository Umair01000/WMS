package com.example.wms.screens.user.apply.presentation.di

import com.example.wms.screens.user.apply.data.repository.ApplyRepositoryImpl
import com.example.wms.screens.user.apply.domain.repository.ApplyRepository
import com.example.wms.screens.user.apply.domain.usecase.SaveApplicationUseCase
import com.example.wms.screens.user.apply.presentation.ApplyViewModel
import com.google.firebase.firestore.FirebaseFirestore

object ApplyModule {

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val repository: ApplyRepository by lazy {
        ApplyRepositoryImpl(firestore)
    }

    private val saveApplicationUseCase: SaveApplicationUseCase by lazy {
        SaveApplicationUseCase(repository)
    }

    fun provideViewModel(): ApplyViewModel {
        return ApplyViewModel(saveApplicationUseCase)
    }
}