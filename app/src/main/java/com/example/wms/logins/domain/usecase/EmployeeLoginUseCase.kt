package com.example.wms.logins.domain.usecase

import com.example.wms.logins.domain.repository.EmployeeLoginRepository
import com.example.wms.utils.LoginResult

class EmployeeLoginUseCase(private val repository: EmployeeLoginRepository) {
    suspend operator fun invoke(email: String, password: String): LoginResult {
        return repository.login(email, password)
    }
}
