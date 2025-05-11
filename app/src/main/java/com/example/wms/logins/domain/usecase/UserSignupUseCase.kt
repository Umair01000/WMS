package com.example.wms.logins.domain.usecase

import com.example.wms.logins.domain.repository.UserLoginRepository
import com.example.wms.utils.LoginResult

class UserSignupUseCase(private val repository: UserLoginRepository) {
    suspend operator fun invoke(email: String, password: String): LoginResult {
        return repository.signup(email, password)
    }
}