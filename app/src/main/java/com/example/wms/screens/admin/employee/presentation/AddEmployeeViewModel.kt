package com.example.wms.screens.admin.employee.presentation

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wms.screens.admin.employee.domain.model.Employee
import com.example.wms.screens.admin.employee.domain.result.EmployeeResult
import com.example.wms.screens.admin.employee.domain.usecase.AddEmployeeUseCase
import kotlinx.coroutines.launch

class AddEmployeeViewModel(
    private val addEmployeeUseCase: AddEmployeeUseCase
) : ViewModel() {

    val emailValid = MutableLiveData<Boolean>()
    val employeeResult = MutableLiveData<EmployeeResult>()
    val isLoading = MutableLiveData<Boolean>()
    private var employeeList: List<Employee> = emptyList()
    val isEmployeesLoaded = MutableLiveData<Boolean>(false)

    init {
        loadEmployees()
    }

    private fun loadEmployees() {
        viewModelScope.launch {
            try {
                // Fetch the list of employees from the repository
                val employees = addEmployeeUseCase.getAllEmployees()

                // Now, populate the employeeList and set the flag to indicate loading is complete
                employeeList = employees
                isEmployeesLoaded.value = true  // Set this after the data has been loaded
                Log.d("AddEmployeeViewModel", "Loaded employees: $employeeList") // Log the loaded employees
            } catch (e: Exception) {
                employeeResult.value = EmployeeResult.Error("Error loading employees: ${e.message}")
            }
        }
    }

    // This function validates the email format
    fun validateEmail(email: String) {
        emailValid.value = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // This function checks if the email already exists in the cached list
    fun checkEmailExists(email: String) {
        isLoading.value = true
        viewModelScope.launch {
            // Wait until employees are loaded
            while (!isEmployeesLoaded.value!!) {
                kotlinx.coroutines.delay(100)  // Keep checking every 100ms
            }

            // Check if the email exists in the cached list
            isLoading.value = false
            if (employeeList.any { it.email == email }) {
                employeeResult.value = EmployeeResult.Error("Employee with this email already exists.")
            } else {
                employeeResult.value = EmployeeResult.Success("Email is valid and does not exist.")
            }
        }
    }

    // This function adds a new employee to the Firestore collection
    fun addEmployee(email: String, password: String) {
        viewModelScope.launch {
            employeeResult.value = EmployeeResult.Loading
            try {
                val employee = Employee(email, password)
                val result = addEmployeeUseCase.execute(employee)
                employeeResult.value = result
            } catch (e: Exception) {
                employeeResult.value = EmployeeResult.Error("Error adding employee: ${e.message}")
            }
        }
    }

    fun getEmployeeByEmail(email: String): Employee? {
        Log.d("SearchEmployeeFragment", "Employee List: $employeeList")
        return employeeList.find { it.email == email }
    }
}
