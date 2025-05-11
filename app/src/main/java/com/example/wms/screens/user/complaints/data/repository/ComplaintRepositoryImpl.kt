package com.example.wms.screens.user.complaints.data.repository

import com.example.wms.screens.user.complaints.domain.model.ComplaintData
import com.example.wms.screens.user.complaints.domain.repository.ComplaintRepository
import com.example.wms.screens.user.complaints.domain.repository.ComplaintResult
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ComplaintRepositoryImpl(
    private val firestore: FirebaseFirestore
) : ComplaintRepository {

    private val complaintCollection = firestore.collection("user_complaints")

    override suspend fun saveComplaint(complaintData: ComplaintData): ComplaintResult {
        return try {
            complaintCollection.add(complaintData).await()
            ComplaintResult.Success("Complaint saved successfully.")
        } catch (e: Exception) {
            ComplaintResult.Error("Failed to save complaint: ${e.localizedMessage}")
        }
    }
}
