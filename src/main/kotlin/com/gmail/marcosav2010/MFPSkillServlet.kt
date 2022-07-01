package com.gmail.marcosav2010

import com.amazon.ask.Skill
import com.amazon.ask.Skills
import com.amazon.ask.servlet.SkillServlet
import com.gmail.marcosav2010.api.MFPApi
import com.gmail.marcosav2010.config.Configuration
import com.gmail.marcosav2010.handlers.FoodPreviewIntentHandler
import com.gmail.marcosav2010.handlers.LaunchHandler
import com.gmail.marcosav2010.useCases.FoodListUseCase

class MFPSkillServlet : SkillServlet(getSkill()) {

    companion object {

        private val foodListUseCase = FoodListUseCase(MFPApi())

        private fun getSkill(): Skill {
            return Skills.standard()
                .addRequestHandlers(
                    LaunchHandler(),
                    FoodPreviewIntentHandler(foodListUseCase)
                )
                .withSkillId(Configuration.skillId)
                .build()
        }
    }
}