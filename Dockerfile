FROM eclipse-temurin:21-jdk-jammy AS build
COPY . /source
WORKDIR /source
RUN ./gradlew war

FROM tomcat:9.0.87-jre21-temurin-jammy
COPY --from=build /source/build/libs/*.war /usr/local/tomcat/webapps/myfitnesspal-alexa-skill.war

CMD "catalina.sh" "run"