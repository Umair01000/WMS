package com.example.wms.logins.presentation.user.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wms.logins.domain.usecase.UserSignupUseCase
import com.example.wms.logins.presentation.di.RepositoryModule
import com.example.wms.framework.utils.LoginResult
import kotlinx.coroutines.launch

class UserSignupViewModel : ViewModel() {
    private val userSignupUseCase = UserSignupUseCase(RepositoryModule.userLoginRepository)
    private val _signupResult = MutableLiveData<LoginResult>()
    val signupResult: LiveData<LoginResult> = _signupResult

    /**
     * Handles user signup.
     */
    fun signup(email: String, password: String) {
        viewModelScope.launch {
            _signupResult.value = LoginResult.Loading
            val result = userSignupUseCase(email, password)
            _signupResult.value = result
        }
    }
}
