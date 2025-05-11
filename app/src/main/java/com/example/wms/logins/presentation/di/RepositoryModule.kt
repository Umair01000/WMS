package com.example.wms.logins.presentation.di

import com.example.wms.logins.data.AdminLoginRepositoryImpl
import com.example.wms.logins.data.EmployeeLoginRepositoryImpl
import com.example.wms.logins.data.UserLoginRepositoryImpl
import com.example.wms.logins.domain.repository.AdminLoginRepository
import com.example.wms.logins.domain.repository.EmployeeLoginRepository
import com.example.wms.logins.domain.repository.UserLoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object RepositoryModule {
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    val adminLoginRepository: AdminLoginRepository by lazy {
        AdminLoginRepositoryImpl(firestore)
    }

    // Initialize FirebaseAuth instance
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    // Provide UserLoginRepository instance
    val userLoginRepository: UserLoginRepository by lazy {
        UserLoginRepositoryImpl(firebaseAuth)
    }

    val employeeLoginRepository: EmployeeLoginRepository by lazy {
        EmployeeLoginRepositoryImpl(firestore)
    }
}