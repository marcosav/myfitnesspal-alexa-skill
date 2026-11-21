FROM eclipse-temurin:21-jdk-jammy@sha256:b2711c65970c1227197c29c46e669bfdbb137524e9da0e664703a140ec25a040 AS build
COPY . /source
WORKDIR /source
RUN ./gradlew war

FROM tomcat:9.0.124-jre21-temurin-jammy@sha256:5928a42280a99ea46f9014f2c9eaede74db1a2ecb3ec215e11c806c2f9173f96
COPY --from=build /source/build/libs/*.war /usr/local/tomcat/webapps/myfitnesspal-alexa-skill.war

CMD ["catalina.sh", "run"]
