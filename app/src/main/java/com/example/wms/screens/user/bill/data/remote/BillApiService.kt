package com.example.wms.screens.user.bill.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface BillApiService {
    @POST("new.php")
    suspend fun getBillHtml(
        @Query("C_Code") cCode: String,
    ): Response<ResponseBody>
}
