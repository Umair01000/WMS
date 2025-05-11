package com.example.wms.application

import android.app.Application
import com.example.wms.logins.data.AdminLoginRepositoryImpl
import com.example.wms.logins.data.EmployeeLoginRepositoryImpl
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WMSApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        // Initialize Admin Credentials
        CoroutineScope(Dispatchers.IO).launch {
            val adminRepository = AdminLoginRepositoryImpl(FirebaseFirestore.getInstance())
            val employeeLoginRepository =
                EmployeeLoginRepositoryImpl(FirebaseFirestore.getInstance())
            adminRepository.initializeAdminCredentials()
            employeeLoginRepository.initializeEmployeeCredentials()
        }
    }
}