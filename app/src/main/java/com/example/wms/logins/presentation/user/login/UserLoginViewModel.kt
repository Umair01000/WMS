package com.example.wms.logins.presentation.user.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wms.logins.domain.usecase.UserLoginUseCase
import com.example.wms.logins.presentation.di.RepositoryModule
import com.example.wms.utils.LoginResult
import kotlinx.coroutines.launch

class UserLoginViewModel : ViewModel() {
    private val userLoginUseCase = UserLoginUseCase(RepositoryModule.userLoginRepository)

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    /**
     * Handles user login.
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = LoginResult.Loading
            val result = userLoginUseCase(email, password)
            _loginResult.value = result
        }
    }
}
