package com.gmail.marcosav2010.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.gmail.marcosav2010.myfitnesspal.api.MFPSession
import java.util.*

class FoodPreviewIntentHandler : IntentRequestHandler {

    override fun canHandle(input: HandlerInput, intentRequest: IntentRequest): Boolean {
        return input.requestEnvelope.request.type == "FoodPreviewIntent"
    }

    override fun handle(input: HandlerInput, intentRequest: IntentRequest): Optional<Response> {
        val username = System.getenv("MFP_USERNAME")
        val password = System.getenv("MFP_PASSWORD")
        val session = MFPSession.create(username, password)

        val mealSlot = intentRequest.intent.slots["Meal"]?.value

        val diary = session.toDiary()
        val day = diary.getFullDay(Date())

        val speakOutput = "Has pedido, $mealSlot"

        return input.responseBuilder
            .withSpeech(speakOutput)
            .build()
    }
}