package com.gmail.marcosav2010.api

import com.gmail.marcosav2010.config.Configuration
import com.gmail.marcosav2010.domain.Food
import com.gmail.marcosav2010.domain.exceptions.NoCredentialsSetException
import com.gmail.marcosav2010.myfitnesspal.api.IMFPSession
import com.gmail.marcosav2010.myfitnesspal.api.MFPSession
import com.gmail.marcosav2010.myfitnesspal.api.diary.Diary
import com.gmail.marcosav2010.myfitnesspal.api.diary.food.DiaryFood
import com.gmail.marcosav2010.myfitnesspal.api.diary.food.DiaryMeal
import java.util.Date

class MFPApi {

    private val cache = hashMapOf<Int, FilledFoodRequest>()

    private var mfpSession = createSession()
        get() {
            if (field.shouldReLog())
                field = createSession()
            return field
        }

    private fun getDayMeals0(date: Date): List<DiaryMeal> = mfpSession.toDiary().getDay(date, Diary.FOOD).meals
    private fun getDayMeals(date: Date): List<DiaryMeal> = getRequestKey(date).let { k ->
        val cached = cache[k]
        if (cached != null && cached.timestamp + CACHE_LIFESPAN >= System.currentTimeMillis())
            cached.result
        else {
            val res = getDayMeals0(date)
            cache[k] = FilledFoodRequest(res, System.currentTimeMillis())
            res
        }
    }

    fun getMealFoodForDay(date: Date, mealAlias: String): List<Food>? =
        getDayMeals(date).find { it.name == mealAlias }?.food.mapToFood()

    private fun getRequestKey(date: Date): Int = "${date.year % 100}${date.month}${date.day}".toInt()

    private fun List<DiaryFood>?.mapToFood() = this?.map { Food(it.name, it.amount, it.unit) }

    private fun createSession(): IMFPSession {
        val username = Configuration.username
        val password = Configuration.password
        if (username == null || password == null)
            throw NoCredentialsSetException()

        return MFPSession.create(username, password)
    }

    companion object {
        private const val CACHE_LIFESPAN = 6 * 3600 * 1000L // 6h
    }
}