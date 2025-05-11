package com.example.wms.screens.user.bill.domain.repository

import com.example.wms.screens.user.bill.domain.model.BillDetails

sealed class BillResult {
    data class Success(val bill: BillDetails, val html: String): BillResult()
    data class Error(val message: String) : BillResult()
}

interface BillDetailsRepository {
    suspend fun fetchBill(cCode: String): BillResult
}
