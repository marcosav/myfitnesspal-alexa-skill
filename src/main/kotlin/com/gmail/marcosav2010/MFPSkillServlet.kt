package com.gmail.marcosav2010

import com.amazon.ask.Skill
import com.amazon.ask.Skills
import com.amazon.ask.servlet.SkillServlet
import com.gmail.marcosav2010.application.usecases.FoodListUseCaseImpl
import com.gmail.marcosav2010.config.Configuration
import com.gmail.marcosav2010.handlers.FoodPreviewIntentHandler
import com.gmail.marcosav2010.handlers.LaunchHandler
import com.gmail.marcosav2010.infrastructure.api.adapter.MFPApi
import com.gmail.marcosav2010.infrastructure.api.connector.MFPConnector
import retrofit2.Retrofit
import retrofit2.create


class MFPSkillServlet : SkillServlet(getSkill()) {

    companion object {

        private val clientConfig = Retrofit.Builder()
            .baseUrl(Configuration.mfpApiServerUrl)
            .build()

        private val connector = clientConfig.create<MFPConnector>()

        private val foodListUseCase = FoodListUseCaseImpl(MFPApi(connector))

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