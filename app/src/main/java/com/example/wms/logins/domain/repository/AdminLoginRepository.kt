package com.example.wms.logins.domain.repository

import com.example.wms.utils.LoginResult


interface AdminLoginRepository {
    suspend fun login(email: String, password: String): LoginResult
    suspend fun initializeAdminCredentials()
}