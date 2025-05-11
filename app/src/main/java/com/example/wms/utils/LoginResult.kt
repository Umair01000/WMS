package com.example.wms.utils

sealed class LoginResult {
    data object Loading : LoginResult()
    data object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
}