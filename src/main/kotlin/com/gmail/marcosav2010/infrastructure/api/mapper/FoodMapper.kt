package com.gmail.marcosav2010.infrastructure.api.mapper

import com.gmail.marcosav2010.domain.model.Food
import com.gmail.marcosav2010.infrastructure.api.dto.FoodDTO

class FoodMapper {

    fun toDomain(dayFood: List<FoodDTO>) = dayFood.map { Food(it.name, it.amount.toFloat(), it.unit) }
}