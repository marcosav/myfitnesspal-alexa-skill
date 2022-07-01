package com.gmail.marcosav2010.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler
import com.amazon.ask.model.LaunchRequest
import com.amazon.ask.model.Response
import java.util.*

class LaunchHandler : LaunchRequestHandler {

    override fun canHandle(input: HandlerInput, launchRequest: LaunchRequest) = true

    override fun handle(input: HandlerInput, launchRequest: LaunchRequest): Optional<Response> {
        return input.responseBuilder
            .withSpeech("Pregúntame ¿qué hay para la cena, comida, merienda o desayuno?")
            .build()
    }
}