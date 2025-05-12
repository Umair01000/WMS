package com.example.wms.logins.presentation.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wms.logins.domain.usecase.LoginUseCase
import com.example.wms.logins.presentation.di.RepositoryModule
import com.example.wms.framework.utils.LoginResult
import kotlinx.coroutines.launch

class AdminLoginViewModel : ViewModel() {

    private val loginUseCase = LoginUseCase(RepositoryModule.adminLoginRepository)

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    init {
        viewModelScope.launch {
            RepositoryModule.adminLoginRepository.initializeAdminCredentials()
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = LoginResult.Loading
            val result = loginUseCase(email, password)
            _loginResult.value = result
        }
    }
}
