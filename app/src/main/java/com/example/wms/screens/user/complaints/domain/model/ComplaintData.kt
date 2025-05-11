package com.example.wms.screens.user.complaints.domain.model

data class ComplaintData(
    val name: String,
    val consumerNumber: String,
    val phoneNumber: String,
    val complaint: String,
    val deviceId: String
)
