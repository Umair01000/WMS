package com.example.wms.screens.user.watertiming.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wms.screens.user.watertiming.domain.model.UserWorkingHours
import com.example.wms.screens.user.watertiming.domain.usecase.GetUserWorkingHoursUseCase
import kotlinx.coroutines.launch

class UserWorkingHoursViewModel(
    private val getUserWorkingHoursUseCase: GetUserWorkingHoursUseCase
) : ViewModel() {

    private val _userWorkingHours = MutableLiveData<UserWorkingHours?>()
    val userWorkingHours: LiveData<UserWorkingHours?> get() = _userWorkingHours

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        fetchUserWorkingHours()
    }

    // Fetch user working hours from Firestore
    fun fetchUserWorkingHours() {
        viewModelScope.launch {
            try {
                val workingHours = getUserWorkingHoursUseCase.execute()
                _userWorkingHours.value = workingHours ?: UserWorkingHours() // Default if null
            } catch (e: Exception) {
                _error.value = "Failed to fetch working hours: ${e.message}"
            }
        }
    }
}
