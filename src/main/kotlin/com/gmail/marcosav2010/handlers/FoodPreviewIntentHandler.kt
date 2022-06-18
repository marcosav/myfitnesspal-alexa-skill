package com.gmail.marcosav2010.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.gmail.marcosav2010.exceptions.NoCredentialsSetException
import com.gmail.marcosav2010.exceptions.NoSpecifiedMealException
import com.gmail.marcosav2010.myfitnesspal.api.IMFPSession
import com.gmail.marcosav2010.myfitnesspal.api.MFPSession
import com.gmail.marcosav2010.myfitnesspal.api.diary.Diary
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class FoodPreviewIntentHandler : IntentRequestHandler {

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
        }

        return input.responseBuilder
            .withSpeech(speakOutput)
            .build()
    }

    private fun IMFPSession.getMealContent(meal: MealType): String {
        val diary = toDiary()
        val day = diary.getDay(getLocalDate(), Diary.FOOD)
        val food = day.meals[meal.ordinal].food

        val content = StringBuilder()
        food.forEachIndexed { i, f ->
            if (i > 0)
                content.append(if (i == food.size - 1) " y " else ", ")
            content.append("${f.amount} de ${f.name}")
        }
        return content.toString()
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