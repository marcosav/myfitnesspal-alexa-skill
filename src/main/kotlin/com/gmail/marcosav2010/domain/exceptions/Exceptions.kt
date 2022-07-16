package com.gmail.marcosav2010.domain.exceptions

import com.gmail.marcosav2010.domain.Meal

class NoCredentialsSetException : Exception()

class NoSpecifiedMealException : Exception()

class NoFoodFoundException(val meal: Meal) : Exception()