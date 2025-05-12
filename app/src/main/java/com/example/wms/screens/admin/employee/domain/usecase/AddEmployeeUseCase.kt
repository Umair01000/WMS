package com.example.wms.screens.admin.employee.domain.usecase

import com.example.wms.screens.admin.employee.domain.model.Employee
import com.example.wms.screens.admin.employee.domain.repository.EmployeeRepository
import com.example.wms.screens.admin.employee.domain.result.EmployeeResult

class AddEmployeeUseCase(
    private val employeeRepository: EmployeeRepository
) {

    suspend fun execute(employee: Employee): EmployeeResult {
        return employeeRepository.addEmployee(employee)
    }

    suspend fun getAllEmployees(): List<Employee> {
        return employeeRepository.getAllEmployees()
    }
}
