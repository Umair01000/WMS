package com.example.wms.screens.user.bill.di

import com.example.wms.screens.user.bill.data.remote.BillApiService
import com.example.wms.screens.user.bill.data.repository.BillDetailsRepositoryImpl
import com.example.wms.screens.user.bill.domain.repository.BillDetailsRepository
import com.example.wms.screens.user.bill.domain.usecase.FetchBillDetailsUseCase
import com.example.wms.screens.user.bill.presentation.BillDetailsViewModel

object BillingModule {

    private val apiService: BillApiService by lazy {
        RetrofitModule.provideApiService()
    }

    private val repository: BillDetailsRepository by lazy {
        BillDetailsRepositoryImpl(apiService)
    }

    val fetchBillUseCase by lazy {
        FetchBillDetailsUseCase(repository)
    }

    fun provideViewModel(): BillDetailsViewModel = BillDetailsViewModel(fetchBillUseCase)
}
