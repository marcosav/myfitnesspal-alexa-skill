package com.gmail.marcosav2010

import com.amazon.ask.Skill
import com.amazon.ask.Skills
import com.amazon.ask.servlet.SkillServlet
import com.gmail.marcosav2010.handlers.FoodPreviewIntentHandler
import com.gmail.marcosav2010.handlers.LaunchHandler

class MFPSkillServlet : SkillServlet(getSkill()) {

    companion object {
        private fun getSkill(): Skill {
            return Skills.standard()
                .addRequestHandlers(
                    LaunchHandler(),
                    FoodPreviewIntentHandler()
                )
                .withSkillId(System.getenv("SKILL_ID"))
                .build()
        }
    }

    //private val client = DynamoDbClient { region = "eu-west-3" }

    /*private suspend fun getSession(username: String, password: String): IMFPSession = client.use { db ->
        val storedSession = db.getStoredSession()

        if (storedSession == null)
            MFPSession.create(username, password).also { db.updateSession(it.encode()) }
        else
            MFPSession.from(storedSession)
    }

    private suspend fun DynamoDbClient.getStoredSession(): String? {
        val keyToGet = mutableMapOf<String, AttributeValue>()
        keyToGet["last"] = AttributeValue.S("session")

        val request = GetItemRequest {
            key = keyToGet
            tableName = "Session"
        }

        val session = getItem(request).item
        return session.toString()
    }

    private suspend fun DynamoDbClient.updateSession(session: String) {
        val values = mutableMapOf<String, AttributeValue>()
        values["last"] = AttributeValue.S(session)

        val request = PutItemRequest {
            item = values
            tableName = "Session"
        }

        putItem(request)
    }*/
}