FROM amazoncorretto:17.0.3-alpine3.15@sha256:c3445d61df8a764cf4f2645a2a9ab92cfe1ace5864e1210efebef3956db49b97 AS build
COPY . /source
WORKDIR /source
ARG GITHUBPACKAGESUSERNAME
ARG GITHUBPACKAGESPASSWORD
ENV ORG_GRADLE_PROJECT_GitHubPackagesUsername=$GITHUBPACKAGESUSERNAME
ENV ORG_GRADLE_PROJECT_GitHubPackagesPassword=$GITHUBPACKAGESPASSWORD
RUN ./gradlew war

FROM tomcat:8.5.81-jre17-temurin@sha256:6ed3bee774a88ea7c8b3ff2a375688f023f2379103e4b06d8736a892d26755fc
COPY --from=build /source/build/libs/*.war /usr/local/tomcat/webapps/myfitnesspal-alexa-skill.war

CMD "catalina.sh" "run"