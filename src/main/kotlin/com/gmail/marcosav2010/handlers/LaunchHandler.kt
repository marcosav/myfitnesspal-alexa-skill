package com.gmail.marcosav2010.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler
import com.amazon.ask.model.LaunchRequest
import com.amazon.ask.model.Response
import com.gmail.marcosav2010.config.MessageHandler.messagesResourceBundle
import com.gmail.marcosav2010.config.MessageHandler.get
import java.util.*

class LaunchHandler : LaunchRequestHandler {

    override fun canHandle(input: HandlerInput, launchRequest: LaunchRequest) = true

    override fun handle(input: HandlerInput, launchRequest: LaunchRequest): Optional<Response> =
        input.responseBuilder
            .withSpeech(input.messagesResourceBundle["response.launch"])
            .build()
}