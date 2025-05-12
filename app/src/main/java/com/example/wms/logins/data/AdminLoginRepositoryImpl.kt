package com.example.wms.logins.data

import com.example.wms.logins.domain.repository.AdminLoginRepository
import com.example.wms.framework.utils.LoginResult
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

    override suspend fun initializeAdminCredentials() {}
}
