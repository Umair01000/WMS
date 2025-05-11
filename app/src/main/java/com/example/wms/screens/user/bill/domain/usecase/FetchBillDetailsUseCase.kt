package com.example.wms.screens.user.bill.domain.usecase

import com.example.wms.screens.user.bill.domain.repository.BillDetailsRepository
import com.example.wms.screens.user.bill.domain.repository.BillResult

class FetchBillDetailsUseCase(
    private val repo: BillDetailsRepository
) {
    suspend operator fun invoke(cCode: String): BillResult =
        repo.fetchBill(cCode)
}
