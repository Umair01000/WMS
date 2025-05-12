package com.example.wms.screens.admin.employee.presentation.di

import com.example.wms.screens.admin.employee.data.repository.EmployeeRepositoryImpl
import com.example.wms.screens.admin.employee.domain.repository.EmployeeRepository
import com.example.wms.screens.admin.employee.domain.usecase.AddEmployeeUseCase
import com.example.wms.screens.admin.employee.presentation.AddEmployeeViewModel
import com.google.firebase.firestore.FirebaseFirestore

object AddEmployeeModule {

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val repository: EmployeeRepository by lazy {
        EmployeeRepositoryImpl(firestore)
    }

    private val addEmployeeEmailUseCase: AddEmployeeUseCase by lazy {
        AddEmployeeUseCase(repository)
    }

    fun provideViewModel(): AddEmployeeViewModel {
        return AddEmployeeViewModel(addEmployeeEmailUseCase)
    }
}
