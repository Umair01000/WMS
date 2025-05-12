package com.example.wms.screens.admin.complaints.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wms.screens.admin.complaints.domain.model.UserComplaint
import com.example.wms.screens.admin.complaints.domain.usecase.GetUserComplaintsUseCase
import kotlinx.coroutines.launch

class AdminComplaintsViewModel(
    private val getUserComplaintsUseCase: GetUserComplaintsUseCase
) : ViewModel() {

    private val _complaintsList = MutableLiveData<List<UserComplaint>?>()
    val complaintsList: LiveData<List<UserComplaint>?> get() = _complaintsList

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        fetchComplaints()
    }

    fun fetchComplaints() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val complaints = getUserComplaintsUseCase.execute()
                _complaintsList.value = complaints
                _loading.value = false
                Log.d("AdminComplaintsViewModel", "Fetched complaints: $complaints")  // Add logging here
            } catch (e: Exception) {
                _loading.value = false
                _error.value = "Failed to fetch complaints: ${e.message}"
                Log.e("AdminComplaintsViewModel", "Error fetching complaints: ${e.message}")  // Log error
            }
        }
    }

    fun filterComplaintsByPhone(phoneNumber: String) {
        viewModelScope.launch {
            val filteredComplaints = _complaintsList.value?.filter {
                it.phoneNumber.contains(phoneNumber)
            }
            _complaintsList.value = filteredComplaints
            Log.d("AdminComplaintsViewModel", "Filtered complaints: $filteredComplaints")  // Log filtered data
        }
    }
}
