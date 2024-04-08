package com.gmail.marcosav2010.infrastructure.api.adapter

import com.gmail.marcosav2010.infrastructure.api.dto.FoodDayResponse

data class FilledFoodRequest(val result: FoodDayResponse, val timestamp: Long)
