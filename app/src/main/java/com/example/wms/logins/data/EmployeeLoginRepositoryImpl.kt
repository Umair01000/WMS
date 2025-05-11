package com.example.wms.logins.data

import com.example.wms.logins.domain.model.EmployeeCredentials
import com.example.wms.logins.domain.repository.EmployeeLoginRepository
import com.example.wms.utils.LoginResult
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class EmployeeLoginRepositoryImpl(
    private val firestore: FirebaseFirestore
) : EmployeeLoginRepository {

    private val collectionName = "employee_login"

    override suspend fun login(email: String, password: String): LoginResult {
        return try {
            val querySnapshot = firestore.collection(collectionName)
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                LoginResult.Success
            } else {
                LoginResult.Error("Invalid email or password.")
            }
        } catch (e: Exception) {
            LoginResult.Error("An error occurred: ${e.message}")
        }
    }

    override suspend fun initializeEmployeeCredentials() {
        try {
            val getId = firestore.collection(collectionName).document().id
            val employeeDoc = firestore.collection(collectionName).document(getId)
            val docSnapshot = employeeDoc.get().await()
            if (!docSnapshot.exists()) {
                val employeeCredentials = EmployeeCredentials(
                    email = "employee@gmail.com",
                    password = "employee123"
                )
                employeeDoc.set(employeeCredentials).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
