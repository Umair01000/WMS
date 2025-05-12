package com.example.wms.screens.admin.complaints.data.repository

import com.example.wms.screens.admin.complaints.domain.model.UserComplaint
import com.example.wms.screens.admin.complaints.domain.repository.AdminComplaintsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AdminComplaintsRepositoryImpl(
    private val firestore: FirebaseFirestore
) : AdminComplaintsRepository {

    private val complaintsCollection = firestore.collection("user_complaints")

    override suspend fun getUserComplaints(): List<UserComplaint> {
        return try {
            val querySnapshot = complaintsCollection.get().await()
            val complaints = mutableListOf<UserComplaint>()
            for (document in querySnapshot) {
                val complaint = document.toObject(UserComplaint::class.java)
                complaints.add(complaint)
            }
            complaints
        } catch (e: Exception) {
            emptyList()  // Return an empty list in case of error
        }
    }
}
