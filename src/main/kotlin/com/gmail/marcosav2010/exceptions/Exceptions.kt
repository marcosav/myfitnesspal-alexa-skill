package com.gmail.marcosav2010.exceptions

import com.gmail.marcosav2010.handlers.MealType

class NoCredentialsSetException : Exception()

class NoSpecifiedMealException : Exception()

class NoFoodFoundException(val meal: MealType) : Exception()