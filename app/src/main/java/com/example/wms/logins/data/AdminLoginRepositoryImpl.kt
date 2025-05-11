package com.example.wms.logins.data

import com.example.wms.logins.domain.repository.AdminLoginRepository
import com.example.wms.utils.LoginResult
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AdminLoginRepositoryImpl(
    private val firestore: FirebaseFirestore
) : AdminLoginRepository {

    private val collectionName = "admin_login"

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

    override suspend fun initializeAdminCredentials() {
        try {
            val getId = firestore.collection(collectionName).document().id
            val adminDoc = firestore.collection(collectionName).document(getId)
            val docSnapshot = adminDoc.get().await()
            if (!docSnapshot.exists()) {
                val adminCredentials = mapOf(
                    "email" to "admin@gmail.com",
                    "password" to "admin123"
                )
                adminDoc.set(adminCredentials).await()
            }
        } catch (e: Exception) {
            // Handle initialization error if necessary
            e.printStackTrace()
        }
    }
}
