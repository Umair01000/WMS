package com.example.wms.logins.presentation.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wms.logins.domain.usecase.EmployeeLoginUseCase
import com.example.wms.logins.presentation.di.RepositoryModule
import com.example.wms.utils.LoginResult
import kotlinx.coroutines.launch

class EmployeeLoginViewModel : ViewModel() {

    private val employeeLoginUseCase =
        EmployeeLoginUseCase(RepositoryModule.employeeLoginRepository)

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = LoginResult.Loading
            val result = employeeLoginUseCase(email, password)
            _loginResult.value = result
        }
    }
}