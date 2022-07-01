package com.gmail.marcosav2010.config

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import java.util.*

object MessageHandler {

    private const val BUNDLE_NAME = "messages"

    val HandlerInput.messagesResourceBundle: ResourceBundle
        get() =
            ResourceBundle.getBundle(BUNDLE_NAME, Locale(requestEnvelope.request.locale))

    operator fun ResourceBundle.get(key: String): String = get(key)
}