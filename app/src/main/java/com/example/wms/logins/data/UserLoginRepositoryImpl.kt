package com.example.wms.logins.data

import com.example.wms.logins.domain.repository.UserLoginRepository
import com.example.wms.framework.utils.LoginResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class UserLoginRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : UserLoginRepository {

    override suspend fun login(email: String, password: String): LoginResult {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = firebaseAuth.currentUser
            if (user != null) {
                LoginResult.Success
            } else {
                LoginResult.Error("Authentication failed.")
            }
        } catch (e: Exception) {
            LoginResult.Error("Login failed: ${e.message}")
        }
    }

    override suspend fun signup(email: String, password: String): LoginResult {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = firebaseAuth.currentUser
            if (user != null) {
                LoginResult.Success
            } else {
                LoginResult.Error("Signup failed.")
            }
        } catch (e: Exception) {
            LoginResult.Error("Signup failed: ${e.message}")
        }
    }

    override suspend fun resetPassword(email: String): LoginResult {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            LoginResult.Success
        } catch (e: Exception) {
            LoginResult.Error("Password reset failed: ${e.message}")
        }
    }
}
