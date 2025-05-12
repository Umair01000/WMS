package com.example.wms.logins.presentation.user.forget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wms.logins.domain.usecase.UserResetPasswordUseCase
import com.example.wms.logins.presentation.di.RepositoryModule
import com.example.wms.framework.utils.LoginResult
import kotlinx.coroutines.launch

class UserForgetPasswordViewModel : ViewModel() {
    private val userResetPasswordUseCase =
        UserResetPasswordUseCase(RepositoryModule.userLoginRepository)
    private val _resetPasswordResult = MutableLiveData<LoginResult>()
    val resetPasswordResult: LiveData<LoginResult> = _resetPasswordResult

    /**
     * Handles password reset.
     */
    fun resetPassword(email: String) {
        viewModelScope.launch {
            _resetPasswordResult.value = LoginResult.Loading
            val result = userResetPasswordUseCase(email)
            _resetPasswordResult.value = result
        }
    }
}