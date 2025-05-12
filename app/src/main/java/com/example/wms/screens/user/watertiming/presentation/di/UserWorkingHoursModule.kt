package com.example.wms.screens.user.workinghours.presentation.di

import com.example.wms.screens.user.watertiming.data.repository.UserWorkingHoursRepositoryImpl
import com.example.wms.screens.user.watertiming.domain.repository.UserWorkingHoursRepository
import com.example.wms.screens.user.watertiming.domain.usecase.GetUserWorkingHoursUseCase
import com.example.wms.screens.user.watertiming.presentation.UserWorkingHoursViewModel
import com.google.firebase.firestore.FirebaseFirestore

object UserWorkingHoursModule {

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val userWorkingHoursRepository: UserWorkingHoursRepository by lazy {
        UserWorkingHoursRepositoryImpl(firestore)
    }

    private val getUserWorkingHoursUseCase: GetUserWorkingHoursUseCase by lazy {
        GetUserWorkingHoursUseCase(userWorkingHoursRepository)
    }

    fun provideViewModel(): UserWorkingHoursViewModel {
        return UserWorkingHoursViewModel(getUserWorkingHoursUseCase)
    }
}
