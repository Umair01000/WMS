package com.example.wms.logins.domain.repository

import com.example.wms.framework.utils.LoginResult

interface UserLoginRepository {
    suspend fun login(email: String, password: String): LoginResult
    suspend fun signup(email: String, password: String): LoginResult
    suspend fun resetPassword(email: String): LoginResult
}