package com.example.wms.screens.admin.timing.domain.model

data class WorkingHours(
    val morningStart: String = "00:00",  // Default "00:00" if no data in Firestore
    val morningEnd: String = "00:00",
    val afternoonStart: String = "00:00",
    val afternoonEnd: String = "00:00",
    val eveningStart: String = "00:00",
    val eveningEnd: String = "00:00"
)
