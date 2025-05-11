package com.example.wms.logins.domain.usecase

import com.example.wms.logins.domain.repository.UserLoginRepository
import com.example.wms.utils.LoginResult

class UserResetPasswordUseCase(private val repository: UserLoginRepository) {
    suspend operator fun invoke(email: String): LoginResult {
        return repository.resetPassword(email)
    }
}
