package com.gmail.marcosav2010.config.meals

import com.gmail.marcosav2010.domain.Meal

data class MealConfig(val meals: List<Meal>) {

    fun byName(name: String) = meals.find { it.name == name }
}


