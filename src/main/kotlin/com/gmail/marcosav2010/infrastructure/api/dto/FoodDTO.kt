package com.gmail.marcosav2010.infrastructure.api.dto

data class FoodDTO(
    val name: String,
    val amount: Double,
    val unit: String? = null
)
