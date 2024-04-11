package com.gmail.marcosav2010.infrastructure.api.connector

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.gmail.marcosav2010.config.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create

object Connectors {
    val MFP = ConnectorConfiguration.DEFAULT_CONFIG.create<MFPConnector>()
}

private object ConnectorConfiguration {

    val DEFAULT_CONFIG: Retrofit = Retrofit.Builder()
        .baseUrl(Configuration.mfpApiServerUrl)
        .addConverterFactory(JacksonConverterFactory.create(ObjectMapper().registerKotlinModule()))
        .build()
}