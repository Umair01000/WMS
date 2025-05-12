package com.example.wms.screens.admin.meterrequests.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wms.screens.admin.meterrequests.domain.model.MeterApplication
import com.example.wms.screens.admin.meterrequests.domain.usecase.GetMeterApplicationsUseCase
import kotlinx.coroutines.launch

class AdminMeterRequestViewModel(
    private val getMeterApplicationsUseCase: GetMeterApplicationsUseCase
) : ViewModel() {

    private val _meterApplicationsList = MutableLiveData<List<MeterApplication>?>()
    val meterApplicationsList: LiveData<List<MeterApplication>?> get() = _meterApplicationsList

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        fetchMeterApplications()
    }

    fun fetchMeterApplications() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val applications = getMeterApplicationsUseCase.execute()
                _meterApplicationsList.value = applications
                _loading.value = false
                Log.d("AdminMeterRequestViewModel", "Fetched meter applications: $applications")
            } catch (e: Exception) {
                _loading.value = false
                _error.value = "Failed to fetch meter applications: ${e.message}"
                Log.e("AdminMeterRequestViewModel", "Error fetching applications: ${e.message}")
            }
        }
    }

    fun filterApplicationsByPhone(phoneNumber: String) {
        viewModelScope.launch {
            val filteredApplications = _meterApplicationsList.value?.filter {
                it.phoneNumber.contains(phoneNumber)
            }
            _meterApplicationsList.value = filteredApplications
            Log.d("AdminMeterRequestViewModel", "Filtered applications: $filteredApplications")
        }
    }
}
