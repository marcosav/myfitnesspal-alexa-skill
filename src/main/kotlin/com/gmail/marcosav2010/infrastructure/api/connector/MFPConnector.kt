package com.gmail.marcosav2010.infrastructure.api.connector

import com.gmail.marcosav2010.infrastructure.api.dto.FoodDaysResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MFPConnector {

    @GET("/v1/food")
    fun getDayFood(@Query("date") date: String, @Query("toDate") toDate: String? = null): Call<FoodDaysResponse>
}