package com.gmail.marcosav2010.config.meals

import com.gmail.marcosav2010.domain.model.Meal

data class MealConfig(val meals: List<Meal> = emptyList()) {

    fun byName(name: String) = meals.find { it.name == name }
}


