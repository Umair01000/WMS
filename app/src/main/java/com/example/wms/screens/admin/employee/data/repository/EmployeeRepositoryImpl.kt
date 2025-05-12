package com.example.wms.screens.admin.employee.data.repository

import android.util.Log
import com.example.wms.screens.admin.employee.domain.model.Employee
import com.example.wms.screens.admin.employee.domain.repository.EmployeeRepository
import com.example.wms.screens.admin.employee.domain.result.EmployeeResult
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class EmployeeRepositoryImpl(
    private val firestore: FirebaseFirestore
) : EmployeeRepository {

    private val employeeCollection = firestore.collection("employee_login")

    override suspend fun addEmployee(employee: Employee): EmployeeResult {
        return try {
            // Simply add the employee without checking if the email already exists
            employeeCollection.document(employee.email).set(employee).await()
            EmployeeResult.Success("Employee added successfully")
        } catch (e: Exception) {
            EmployeeResult.Error("Error saving employee: ${e.localizedMessage}")
        }
    }

    override suspend fun getAllEmployees(): List<Employee> {
        return try {
            val querySnapshot = employeeCollection.get().await()
            val employees = mutableListOf<Employee>()
            Log.d("EmployeeRepository", "Number of documents: ${querySnapshot.size()}")
            Log.d("EmployeeRepository", "Documents: ${querySnapshot.documents}")

            for (document in querySnapshot) {
                // Add a check to see if document contains expected fields
                val employee = document.toObject(Employee::class.java)
                Log.d("EmployeeRepository", "Employee: $employee")

                // Log if mapping fails (null or missing values)
                if (employee?.email.isNullOrEmpty() || employee?.password.isNullOrEmpty()) {
                    Log.d("EmployeeRepository", "Mapped employee has empty or null fields!")
                }

                employees.add(employee)
            }
            employees
        } catch (e: Exception) {
            Log.e("EmployeeRepository", "Error loading employees: ${e.message}")
            emptyList()  // Return an empty list if there's an error
        }
    }
}

