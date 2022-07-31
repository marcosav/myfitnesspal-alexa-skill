package com.gmail.marcosav2010.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.gmail.marcosav2010.config.MessageHandler.messagesResourceBundle
import com.gmail.marcosav2010.config.MessageHandler.get
import com.gmail.marcosav2010.domain.exceptions.NoCredentialsSetException
import com.gmail.marcosav2010.domain.exceptions.NoFoodFoundException
import com.gmail.marcosav2010.domain.exceptions.NoSpecifiedMealException
import com.gmail.marcosav2010.useCases.FoodListUseCase
import java.util.*


class FoodPreviewIntentHandler(private val foodListUseCase: FoodListUseCase) : IntentRequestHandler {

    override fun canHandle(input: HandlerInput, intentRequest: IntentRequest): Boolean {
        return intentRequest.intent.name == "FoodPreviewIntent"
    }

    override fun handle(input: HandlerInput, intentRequest: IntentRequest): Optional<Response> {
        val r = input.messagesResourceBundle

        val speakOutput = try {
            val mealName = intentRequest.getMealName()
            val (meal, content, shifted) = foodListUseCase(mealName, r)

            val tomorrow = if (shifted) r["tomorrow"] else ""

            r["response.success"].format(meal.action, tomorrow, content)
        } catch (ex: NoCredentialsSetException) {
            r["response.error.user.not_configured"]
        } catch (ex: NoSpecifiedMealException) {
            r["response.error.meal.unset"]
        } catch (ex: NoFoodFoundException) {
            r["response.error.meal.empty"].format(ex.meal.action)
        } catch (ex: Exception) {
            ex.printStackTrace(System.err)
            r["response.error.unknown"]
        }

        return input.responseBuilder
            .withSpeech(speakOutput)
            .build()
    }

    private fun IntentRequest.getMealName(): String = intent.slots["Meal"]?.resolutions
        ?.resolutionsPerAuthority?.getOrNull(0)?.values?.getOrNull(0)?.value?.name
        ?: throw NoSpecifiedMealException()
}