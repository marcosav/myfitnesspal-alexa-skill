package com.gmail.marcosav2010.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.gmail.marcosav2010.exceptions.NoCredentialsSetException
import com.gmail.marcosav2010.exceptions.NoFoodFoundException
import com.gmail.marcosav2010.exceptions.NoSpecifiedMealException
import com.gmail.marcosav2010.myfitnesspal.api.IMFPSession
import com.gmail.marcosav2010.myfitnesspal.api.MFPSession
import com.gmail.marcosav2010.myfitnesspal.api.diary.Diary
import com.gmail.marcosav2010.myfitnesspal.api.diary.food.DiaryFood
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import java.util.regex.Pattern
import kotlin.math.roundToInt

class FoodPreviewIntentHandler : IntentRequestHandler {

    private val gramAliases = getGramAliases()

    override fun canHandle(input: HandlerInput, intentRequest: IntentRequest): Boolean {
        return intentRequest.intent.name == "FoodPreviewIntent"
    }

    override fun handle(input: HandlerInput, intentRequest: IntentRequest): Optional<Response> {
        val speakOutput = try {
            val session = createSession()
            val meal = intentRequest.getMealType()
            val content = session.getMealContent(meal)

            "Para ${meal.action} tienes, $content"
        } catch (ex: NoCredentialsSetException) {
            "El usuario de MyFitnessPal no está configurado"
        } catch (ex: NoSpecifiedMealException) {
            "Di, ¿qué hay para la cena, comida, merienda o desayuno?"
        } catch (ex: NoFoodFoundException) {
            "No tienes nada para ${ex.meal.action}"
        } catch (ex: Exception) {
            ex.printStackTrace(System.err)
            "Ha ocurrido un error: ${ex.message}"
        }

        return input.responseBuilder
            .withSpeech(speakOutput)
            .build()
    }

    private fun IMFPSession.getMealContent(meal: MealType): String {
        val diary = toDiary()
        val day = diary.getDay(getLocalDate(), Diary.FOOD)
        val food = day.meals[meal.ordinal].food

        if (food.isEmpty())
            throw NoFoodFoundException(meal)

        val content = StringBuilder()
        food.forEachIndexed { i, f ->
            if (i > 0)
                content.append(if (i == food.size - 1) " y " else ", ")
            content.append(f.formatted())
        }
        return content.toString()
    }

    private fun DiaryFood.formatted(): String {
        val formattedAmount = amount.roundToHalf().toString().replaceFirst(Pattern.compile("\\.0$").toRegex(), "")
        val u = if (unit.lowercase() in gramAliases) {
            if (amount == 1.0f) "gramo" else "gramos"
        } else {
            if (amount == 1.0f) "unidad" else "unidades"
        }
        return "$formattedAmount $u de $name"
    }

    private fun Float.roundToHalf(): Float {
        return (this * 2).roundToInt() / 2.0f;
    }

    private fun getGramAliases(): Set<String> {
        val gramAliases = System.getenv("GRAM_ALIASES") ?: ""
        val parsed = gramAliases.lowercase().split(",")
        return parsed.toHashSet()
    }

    private fun getLocalDate() = Date(
        ZonedDateTime.now(ZoneId.of("Europe/Madrid")).toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant()
            .toEpochMilli()
    )

    private fun IntentRequest.getMealType(): MealType = (intent.slots["Meal"]?.resolutions
        ?.resolutionsPerAuthority?.getOrNull(0)?.values?.getOrNull(0)?.value?.id
        ?: throw NoSpecifiedMealException()).let { MealType.valueOf(it) }

    private fun createSession(): IMFPSession {
        val username = System.getenv("MFP_USERNAME")
        val password = System.getenv("MFP_PASSWORD")
        if (username == null || password == null)
            throw NoCredentialsSetException()

        return MFPSession.create(username, password)
    }
}

enum class MealType(val action: String) {
    BREAKFAST("desayunar"), LAUNCH("comer"), DINNER("cenar"), OTHER("merendar")
}