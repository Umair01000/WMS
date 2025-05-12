package com.example.wms.screens.admin.complaints.domain.model

data class UserComplaint(
    val name: String = "",
    val consumerNumber: String = "",
    val phoneNumber: String = "",
    val complaint: String = "",
    val deviceId: String = ""
)
