package com.example.wms.screens.user.complaints.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wms.screens.user.complaints.domain.model.ComplaintData
import com.example.wms.screens.user.complaints.domain.repository.ComplaintResult
import com.example.wms.screens.user.complaints.domain.usecase.SaveComplaintUseCase
import kotlinx.coroutines.launch

class ComplaintViewModel(
    private val saveComplaintUseCase: SaveComplaintUseCase
) : ViewModel() {

    val complaintResult = MutableLiveData<ComplaintResult>()

    fun submitComplaint(complaintData: ComplaintData) {
        Log.d(
            "ComplaintViewModel",
            "ComplaintData: name=${complaintData.name}, consumerNumber=${complaintData.consumerNumber}, phoneNumber=${complaintData.phoneNumber}, complaint=${complaintData.complaint}, deviceId=${complaintData.deviceId}"
        )

        viewModelScope.launch {
            complaintResult.value = ComplaintResult.Loading
            val result = saveComplaintUseCase.execute(complaintData)
            complaintResult.value = result
        }
    }
}
