package com.gmail.marcosav2010.infrastructure.api.dto

import java.util.*

data class FoodDayDTO(
    val date: Date,
    val meals: List<DayMealDTO>
)

