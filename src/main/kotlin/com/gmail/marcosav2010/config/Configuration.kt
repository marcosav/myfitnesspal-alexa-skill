package com.gmail.marcosav2010.config

object Configuration {

    val skillId: String? = get("SKILL_ID")

    val username: String? = get("MFP_USERNAME")

    val password: String? = get("MFP_PASSWORD")

    val gramAliases: Set<String> = parseGramAliases()

    private fun get(env: String) = System.getenv(env)

    private fun parseGramAliases(): Set<String> {
        val gramAliases = get("GRAM_ALIASES") ?: ""
        val parsed = gramAliases.lowercase().split(",")
        return parsed.toHashSet()
    }
}