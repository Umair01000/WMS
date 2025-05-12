package com.example.wms.screens.admin.employee.domain.result

sealed class EmployeeResult {
    data object Loading : EmployeeResult()
    data class Success(val message: String) : EmployeeResult()
    data class Error(val message: String) : EmployeeResult()
}
