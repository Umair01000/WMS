package com.example.wms.logins.domain.usecase

import com.example.wms.logins.domain.repository.AdminLoginRepository
import com.example.wms.framework.utils.LoginResult

class LoginUseCase(private val repository: AdminLoginRepository) {
    suspend operator fun invoke(email: String, password: String): LoginResult {
        return repository.login(email, password)
    }
}