package com.gmail.marcosav2010.domain.usecases

import com.gmail.marcosav2010.domain.model.Meal
import java.util.*

fun interface FoodListUseCase {

    operator fun invoke(mealName: String, r: ResourceBundle): Triple<Meal, String, Boolean>
}