package com.example.wms.logins.domain.usecase

import com.example.wms.logins.domain.repository.UserLoginRepository
import com.example.wms.utils.LoginResult

class UserLoginUseCase(private val repository: UserLoginRepository) {
    suspend operator fun invoke(email: String, password: String): LoginResult {
        return repository.login(email, password)
    }
}