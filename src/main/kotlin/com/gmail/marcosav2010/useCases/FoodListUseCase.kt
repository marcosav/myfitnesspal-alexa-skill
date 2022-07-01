package com.gmail.marcosav2010.useCases

import com.gmail.marcosav2010.api.MFPApi
import com.gmail.marcosav2010.config.Configuration
import com.gmail.marcosav2010.config.Configuration.threshold
import com.gmail.marcosav2010.domain.Food
import com.gmail.marcosav2010.domain.MealType
import com.gmail.marcosav2010.domain.exceptions.NoFoodFoundException
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import java.util.regex.Pattern
import kotlin.math.roundToInt

class FoodListUseCase(private val mfpApi: MFPApi) {

    fun getForMeal(meal: MealType): Pair<String, Boolean> {
        val (date, shifted) = getLocalDate().withShiftedDay(meal)
        val food = mfpApi.getMealFoodForDay(date, meal.alias)

        if (food.isNullOrEmpty())
            throw NoFoodFoundException(meal)

        val content = StringBuilder()
        food.forEachIndexed { i, f ->
            if (i > 0)
                content.append(if (i == food.size - 1) " y " else ", ")
            content.append(f.formatted())
        }
        return content.toString() to shifted
    }

    private fun Date.withShiftedDay(meal: MealType): Pair<Date, Boolean> {
        val threshold = meal.threshold ?: return this to false
        return if (hours >= threshold) {
            val c = Calendar.getInstance()
            c.time = this
            c.add(Calendar.DAY_OF_YEAR, 1)
            c.time to true
        } else this to false
    }

    private fun getLocalDate() =
        Date(ZonedDateTime.now(TIMEZONE).toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())

    private fun Food.formatted(): String {
        val formattedAmount = amount.roundToHalf().toString()
            .replaceFirst(Pattern.compile("\\.0$").toRegex(), "")
        val u = if (unit.lowercase() in Configuration.gramAliases) {
            if (amount == 1.0f) GRAM_SINGULAR else GRAM_PLURAL
        } else {
            if (amount == 1.0f) UNIT_SINGULAR else UNIT_PLURAL
        }
        return "$formattedAmount $u de $name"
    }

    private fun Float.roundToHalf() = (this * 2).roundToInt() / 2.0f

    companion object {

        private val TIMEZONE = ZoneId.of("Europe/Madrid")

        private const val GRAM_SINGULAR = "gramo"
        private const val GRAM_PLURAL = "gramos"
        private const val UNIT_SINGULAR = "unidad"
        private const val UNIT_PLURAL = "unidades"
    }
}