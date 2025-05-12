package com.example.wms.screens.admin.meterrequests.data.repository

import com.example.wms.screens.admin.meterrequests.domain.model.MeterApplication
import com.example.wms.screens.admin.meterrequests.domain.repository.AdminMeterRequestRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AdminMeterRequestRepositoryImpl(
    private val firestore: FirebaseFirestore
) : AdminMeterRequestRepository {

    private val meterApplicationsCollection = firestore.collection("meter_applications")

    override suspend fun getMeterApplications(): List<MeterApplication> {
        return try {
            val querySnapshot = meterApplicationsCollection.get().await()
            val applications = mutableListOf<MeterApplication>()
            for (document in querySnapshot) {
                val application = document.toObject(MeterApplication::class.java)
                applications.add(application)
            }
            applications
        } catch (e: Exception) {
            emptyList()
        }
    }
}
