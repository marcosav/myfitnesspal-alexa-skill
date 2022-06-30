package com.gmail.marcosav2010.domain.exceptions

import com.gmail.marcosav2010.domain.MealType

class NoCredentialsSetException : Exception()

class NoSpecifiedMealException : Exception()

class NoFoodFoundException(val meal: MealType) : Exception()