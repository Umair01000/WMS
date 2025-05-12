package com.example.wms.screens.admin.timing.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wms.framework.utils.TimeValidator
import com.example.wms.screens.admin.timing.domain.model.WorkingHours
import com.example.wms.screens.admin.timing.domain.usecase.GetWorkingHoursUseCase
import com.example.wms.screens.admin.timing.domain.usecase.SaveWorkingHoursUseCase
import kotlinx.coroutines.launch

class AdminTimerViewModel(
    private val saveWorkingHoursUseCase: SaveWorkingHoursUseCase,
    private val getWorkingHoursUseCase: GetWorkingHoursUseCase
) : ViewModel() {

    private val _workingHours = MutableLiveData<WorkingHours?>()
    val workingHours: LiveData<WorkingHours?> get() = _workingHours

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> get() = _success

    init {
        fetchWorkingHours()
    }

    fun fetchWorkingHours() {
        viewModelScope.launch {
            val hours = getWorkingHoursUseCase.execute()
            _workingHours.value = hours ?: WorkingHours()
        }
    }

    fun saveWorkingHours(workingHours: WorkingHours) {
            viewModelScope.launch {
                Log.d("AdminTimerViewModel", "Saving working hours: $workingHours")
                val result = saveWorkingHoursUseCase.execute(workingHours)
                _success.value = result
            }
    }

    private fun isValidWorkingHours(workingHours: WorkingHours): Boolean {
        return TimeValidator.isValidTimeRange(workingHours.morningStart, workingHours.morningEnd) &&
                TimeValidator.isValidTimeRange(
                    workingHours.afternoonStart,
                    workingHours.afternoonEnd
                ) &&
                TimeValidator.isValidTimeRange(workingHours.eveningStart, workingHours.eveningEnd)
    }
}
