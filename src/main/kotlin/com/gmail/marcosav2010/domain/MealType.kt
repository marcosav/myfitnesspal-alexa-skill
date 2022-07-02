package com.gmail.marcosav2010.domain

enum class MealType(val alias: String, val action: String) {

    BREAKFAST("Desayuno", "desayunar"),
    LAUNCH("Comida", "comer"),
    DINNER("Cena", "cenar"),
    OTHER("Merienda/Otros", "merendar");

    companion object {

        fun byAlias(alias: String) = values().find { it.alias == alias }
    }
}