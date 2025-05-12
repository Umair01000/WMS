package com.example.wms.screens.admin.complaints.domain.repository

import com.example.wms.screens.admin.complaints.domain.model.UserComplaint

interface AdminComplaintsRepository {
    suspend fun getUserComplaints(): List<UserComplaint>
}
