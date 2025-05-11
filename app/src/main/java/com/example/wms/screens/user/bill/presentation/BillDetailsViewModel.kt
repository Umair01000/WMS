package com.example.wms.screens.user.bill.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wms.screens.user.bill.domain.repository.BillResult
import com.example.wms.screens.user.bill.domain.usecase.FetchBillDetailsUseCase
import com.example.wms.screens.user.bill.presentation.state.BillDetailsState
import kotlinx.coroutines.launch

class BillDetailsViewModel(
    private val fetchUseCase: FetchBillDetailsUseCase
) : ViewModel() {

    private val _state = MutableLiveData<BillDetailsState>(BillDetailsState.Idle)
    val state: LiveData<BillDetailsState> = _state

    fun loadBill(cCode: String) {
        viewModelScope.launch {
            _state.value = BillDetailsState.Loading
            when (val res = fetchUseCase(cCode)) {
                is BillResult.Success -> _state.value = BillDetailsState.Success(res.bill, res.html)
                is BillResult.Error -> _state.value = BillDetailsState.Error(res.message)
            }
        }
    }
}
