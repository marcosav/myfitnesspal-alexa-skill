package com.gmail.marcosav2010.application.ports

import com.gmail.marcosav2010.domain.model.Food
import java.util.*

interface FoodPort {

    fun getMealFoodForDay(date: Date, mealName: String): List<Food>?
}