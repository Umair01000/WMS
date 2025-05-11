package com.example.wms.logins.domain.repository

import com.example.wms.utils.LoginResult

interface EmployeeLoginRepository {
    suspend fun login(email: String, password: String): LoginResult
    suspend fun initializeEmployeeCredentials()
}
