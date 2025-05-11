package com.example.wms.screens.user.bill.di

import com.example.wms.screens.user.bill.data.remote.BillApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitModule {
    private const val BASE_URL = "https://www.wasarwp.gop.pk/"

    private val client = OkHttpClient.Builder().build()

    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

    fun provideApiService(): BillApiService =
        provideRetrofit().create(BillApiService::class.java)
}
