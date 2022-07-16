package com.gmail.marcosav2010.useCases

import com.gmail.marcosav2010.api.MFPApi
import com.gmail.marcosav2010.config.Configuration
import com.gmail.marcosav2010.config.MessageHandler.get
import com.gmail.marcosav2010.config.meals.MealsConfigParser
import com.gmail.marcosav2010.domain.Food
import com.gmail.marcosav2010.domain.Meal
import com.gmail.marcosav2010.domain.exceptions.NoFoodFoundException
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import java.util.regex.Pattern
import kotlin.math.roundToInt

class FoodListUseCase(private val mfpApi: MFPApi) {

    operator fun invoke(mealName: String, r: ResourceBundle): Triple<Meal, String, Boolean> {
        val meal = parseMeal(mealName)
        val (date, shifted) = getLocalDate().withShiftedDay(meal)
        val food = mfpApi.getMealFoodForDay(date, meal.name)

        if (food.isNullOrEmpty())
            throw NoFoodFoundException(meal)

        val content = StringBuilder()
        food.forEachIndexed { i, f ->
            if (i > 0)
                content.append(if (i == food.size - 1) " y " else ", ")
            content.append(f.formatted(r))
        }
        return Triple(meal, content.toString(), shifted)
    }

    private fun parseMeal(meal: String): Meal = MealsConfigParser.config.byName(meal)!!

    private fun Date.withShiftedDay(meal: Meal): Pair<Date, Boolean> {
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

    private fun Food.formatted(r: ResourceBundle): String {
        val formattedAmount = amount.roundToHalf().toString()
            .replaceFirst(Pattern.compile("\\.0$").toRegex(), "")
        val u = if (unit.lowercase() in Configuration.gramAliases) {
            if (amount == 1.0f) r["gram.singular"] else r["gram.plural"]
        } else {
            if (amount == 1.0f) r["unit.singular"] else r["unit.plural"]
        }
        return "$formattedAmount $u ${r["of"]} $name"
    }

    private fun Float.roundToHalf() = (this * 2).roundToInt() / 2.0f

    companion object {

        private val TIMEZONE = ZoneId.of(Configuration.timezone)
    }
}