package com.gmail.marcosav2010.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.gmail.marcosav2010.api.MFPApi
import com.gmail.marcosav2010.domain.MealType
import com.gmail.marcosav2010.domain.exceptions.NoCredentialsSetException
import com.gmail.marcosav2010.domain.exceptions.NoFoodFoundException
import com.gmail.marcosav2010.domain.exceptions.NoSpecifiedMealException
import com.gmail.marcosav2010.useCases.FoodListUseCase
import java.util.*

class FoodPreviewIntentHandler : IntentRequestHandler {

    private val foodService = FoodListUseCase(MFPApi())

    override fun canHandle(input: HandlerInput, intentRequest: IntentRequest): Boolean {
        return intentRequest.intent.name == "FoodPreviewIntent"
    }

    override fun handle(input: HandlerInput, intentRequest: IntentRequest): Optional<Response> {
        val speakOutput = try {
            val meal = intentRequest.getMealType()
            val content = foodService.getForMeal(meal)

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

    private fun IntentRequest.getMealType(): MealType = (intent.slots["Meal"]?.resolutions
        ?.resolutionsPerAuthority?.getOrNull(0)?.values?.getOrNull(0)?.value?.id
        ?: throw NoSpecifiedMealException()).let { MealType.valueOf(it) }
}