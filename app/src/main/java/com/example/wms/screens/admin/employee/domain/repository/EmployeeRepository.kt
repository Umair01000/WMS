package com.example.wms.screens.admin.employee.domain.repository

import com.example.wms.screens.admin.employee.domain.model.Employee
import com.example.wms.screens.admin.employee.domain.result.EmployeeResult

interface EmployeeRepository {
    suspend fun addEmployee(employee: Employee): EmployeeResult
    suspend fun getAllEmployees(): List<Employee>
}
