package com.gmail.marcosav2010.infrastructure.api.adapter

import com.gmail.marcosav2010.infrastructure.api.dto.FoodDayDTO

data class FilledFoodRequest(val result: FoodDayDTO, val timestamp: Long)
