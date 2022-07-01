package com.gmail.marcosav2010.api

import com.gmail.marcosav2010.myfitnesspal.api.diary.food.DiaryMeal

data class FilledFoodRequest(val result: List<DiaryMeal>, val timestamp: Long)
