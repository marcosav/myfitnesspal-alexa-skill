package com.gmail.marcosav2010.infrastructure.api.connector

import com.gmail.marcosav2010.infrastructure.api.dto.FoodDayResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface MFPConnector {

    @GET("/v1/food")
    fun getDayFood(@Query("sort") date: Date, @Query("sort") toDate: Date? = null): FoodDayResponse
}