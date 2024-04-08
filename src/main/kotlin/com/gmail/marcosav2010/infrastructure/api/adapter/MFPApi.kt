package com.gmail.marcosav2010.infrastructure.api.adapter

import com.gmail.marcosav2010.application.ports.FoodPort
import com.gmail.marcosav2010.config.Configuration
import com.gmail.marcosav2010.domain.model.Food
import com.gmail.marcosav2010.infrastructure.api.connector.MFPConnector
import com.gmail.marcosav2010.infrastructure.api.dto.FoodDayResponse
import com.gmail.marcosav2010.infrastructure.api.mapper.FoodMapper
import java.util.*

class MFPApi(
    private val connector: MFPConnector,
    private val mapper: FoodMapper = FoodMapper()
) : FoodPort {

    private val dayFoodCache = hashMapOf<Int, FilledFoodRequest>()

    private fun getDayMeals0(date: Date): FoodDayResponse = connector.getDayFood(date)

    private fun getDayMeals(date: Date): FoodDayResponse = getRequestKey(date).let { k ->
        val cached = dayFoodCache[k]
        if (cached != null && cached.timestamp + CACHE_LIFESPAN >= System.currentTimeMillis())
            cached.result
        else
            getDayMeals0(date).also { dayFoodCache[k] = FilledFoodRequest(it, System.currentTimeMillis()) }
    }

    override fun getMealFoodForDay(date: Date, mealName: String): List<Food>? =
        getDayMeals(date).meals.find { it.name == mealName }?.food?.let { mapper.toDomain(it) }

    private fun getRequestKey(date: Date): Int = "${date.year % 100}${date.month}${date.day}".toInt()

    companion object {

        private val CACHE_LIFESPAN = Configuration.cacheLifespan * 1000L
    }
}