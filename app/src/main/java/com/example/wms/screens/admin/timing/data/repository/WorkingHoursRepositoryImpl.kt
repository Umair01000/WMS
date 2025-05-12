package com.example.wms.screens.admin.timing.data.repository

import com.example.wms.screens.admin.timing.domain.model.WorkingHours
import com.example.wms.screens.admin.timing.domain.repository.WorkingHoursRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class WorkingHoursRepositoryImpl(
    private val firestore: FirebaseFirestore
) : WorkingHoursRepository {

    private val workingHoursCollection = firestore.collection("working_hours")

    override suspend fun getWorkingHours(): WorkingHours? {
        return try {
            val documentSnapshot =
                workingHoursCollection.document("current_working_hours").get().await()
            if (documentSnapshot.exists()) {
                documentSnapshot.toObject(WorkingHours::class.java)
            } else {
                WorkingHours()
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveWorkingHours(workingHours: WorkingHours): Boolean {
        return try {
            workingHoursCollection.document("current_working_hours").set(workingHours).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
