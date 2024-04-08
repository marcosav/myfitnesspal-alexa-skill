package com.gmail.marcosav2010.config

object Configuration {

    val skillId: String? = get("SKILL_ID")

    val mfpApiServerUrl: String = get("MFP_API_SERVER_URL")

    val gramAliases: Set<String> = parseGramAliases()

    val timezone: String = get("TIMEZONE")

    val cacheLifespan: Long = get("CACHE_LIFESPAN").toLongOrNull() ?: 0

    private fun get(env: String) = System.getenv(env)

    private fun parseGramAliases(): Set<String> {
        val gramAliases = get("GRAM_ALIASES") ?: ""
        val parsed = gramAliases.lowercase().split(",")
        return parsed.toHashSet()
    }
}