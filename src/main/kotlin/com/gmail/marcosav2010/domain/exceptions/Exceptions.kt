package com.gmail.marcosav2010.domain.exceptions

import com.gmail.marcosav2010.domain.model.Meal

class NoSpecifiedMealException : Exception()

class NoFoodFoundException(val meal: Meal) : Exception()