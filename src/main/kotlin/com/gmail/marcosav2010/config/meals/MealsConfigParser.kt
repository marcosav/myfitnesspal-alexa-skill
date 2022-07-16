package com.gmail.marcosav2010.config.meals

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.io.File
import java.io.FileOutputStream

object MealsConfigParser {

    private const val CONFIG_PATH = "meals.yml"

    val config: MealConfig by lazy { parse() }

    private fun copyDefault() {
        javaClass.classLoader.getResourceAsStream(CONFIG_PATH)?.transferTo(FileOutputStream(CONFIG_PATH))
    }

    private fun parse(): MealConfig {
        val file = File(CONFIG_PATH)
        if (!file.exists()) copyDefault()

        val oMapper = ObjectMapper(YAMLFactory())
        return oMapper.readValue(file, MealConfig::class.java)
    }
}
