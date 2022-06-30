package com.gmail.marcosav2010.api

import com.gmail.marcosav2010.config.Configuration
import com.gmail.marcosav2010.domain.Food
import com.gmail.marcosav2010.domain.exceptions.NoCredentialsSetException
import com.gmail.marcosav2010.myfitnesspal.api.IMFPSession
import com.gmail.marcosav2010.myfitnesspal.api.MFPSession
import com.gmail.marcosav2010.myfitnesspal.api.diary.Diary
import com.gmail.marcosav2010.myfitnesspal.api.diary.food.DiaryFood
import java.util.Date

class MFPApi {

    private val mfpSession = createSession()

    fun getMealFoodForDay(date: Date, mealAlias: String): List<Food>? {
        val diary = mfpSession.toDiary()
        val day = diary.getDay(date, Diary.FOOD)
        return day.meals.find { it.name == mealAlias }?.food.mapToFood()
    }

    private fun List<DiaryFood>?.mapToFood() = this?.map { Food(it.name, it.amount, it.unit) }

    private fun createSession(): IMFPSession {
        val username = Configuration.username
        val password = Configuration.password
        if (username == null || password == null)
            throw NoCredentialsSetException()

        return MFPSession.create(username, password)
    }
}