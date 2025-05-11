package com.example.wms.screens.user.apply.data.repository

import com.example.wms.screens.user.apply.domain.model.ApplyData
import com.example.wms.screens.user.apply.domain.repository.ApplyRepository
import com.example.wms.screens.user.apply.domain.repository.ApplyResult
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ApplyRepositoryImpl(
    private val firestore: FirebaseFirestore
) : ApplyRepository {

    private val applyCollection = firestore.collection("meter_applications")

    override suspend fun saveApplication(applyData: ApplyData): ApplyResult {
        return try {
            applyCollection.add(applyData).await()
            ApplyResult.Success("Application saved successfully.")
        } catch (e: Exception) {
            ApplyResult.Error("Failed to save application: ${e.localizedMessage}")
        }
    }
}
