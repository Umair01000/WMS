package com.example.wms.screens.user.watertiming.data.repository

import com.example.wms.screens.user.watertiming.domain.model.UserWorkingHours
import com.example.wms.screens.user.watertiming.domain.repository.UserWorkingHoursRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserWorkingHoursRepositoryImpl(
    private val firestore: FirebaseFirestore
) : UserWorkingHoursRepository {

    private val workingHoursCollection = firestore.collection("working_hours")

    override suspend fun getUserWorkingHours(): UserWorkingHours? {
        return try {
            val documentSnapshot =
                workingHoursCollection.document("current_working_hours").get().await()
            if (documentSnapshot.exists()) {
                documentSnapshot.toObject(UserWorkingHours::class.java)
            } else {
                UserWorkingHours() // Default to "00:00" if no data exists
            }
        } catch (e: Exception) {
            null
        }
    }
}
