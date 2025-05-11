package com.example.wms.screens.user.bill.presentation.state

import com.example.wms.screens.user.bill.domain.model.BillDetails

sealed class BillDetailsState {
    object Idle : BillDetailsState()
    object Loading : BillDetailsState()
    data class Success(val bill: BillDetails, val html: String): BillDetailsState()
    data class Error(val message: String) : BillDetailsState()
}
