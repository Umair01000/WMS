package com.example.wms.screens.user.apply.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wms.screens.user.apply.domain.model.ApplyData
import com.example.wms.screens.user.apply.domain.repository.ApplyResult
import com.example.wms.screens.user.apply.domain.usecase.SaveApplicationUseCase
import kotlinx.coroutines.launch

class ApplyViewModel(
    private val saveApplicationUseCase: SaveApplicationUseCase
) : ViewModel() {

    val applicationResult = MutableLiveData<ApplyResult>()

    fun applyForService(applyData: ApplyData) {
        Log.d("ApplyViewModel", "ApplyData: name=${applyData.name}, cnic=${applyData.cnic}, phoneNumber=${applyData.phoneNumber}, address=${applyData.address}")

        viewModelScope.launch {
            applicationResult.value = ApplyResult.Loading
            val result = saveApplicationUseCase.execute(applyData)
            applicationResult.value = result
        }
    }
}
