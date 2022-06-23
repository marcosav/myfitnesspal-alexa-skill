FROM amazoncorretto:17.0.3-alpine3.15@sha256:c3445d61df8a764cf4f2645a2a9ab92cfe1ace5864e1210efebef3956db49b97 AS build
COPY . /source
WORKDIR /source
ARG GITHUBPACKAGESUSERNAME
ARG GITHUBPACKAGESPASSWORD
ENV ORG_GRADLE_PROJECT_GitHubPackagesUsername=$GITHUBPACKAGESUSERNAME
ENV ORG_GRADLE_PROJECT_GitHubPackagesPassword=$GITHUBPACKAGESPASSWORD
RUN ./gradlew shadowJar -PGitHubPackagesUsername -PGitHubPackagesPassword

FROM amazoncorretto:17.0.3-alpine3.15@sha256:c3445d61df8a764cf4f2645a2a9ab92cfe1ace5864e1210efebef3956db49b97
COPY --from=build /source/build/libs/*.jar /app/mfp-alexa-skill.jar
WORKDIR /app
CMD "java" "-jar" "mfp-alexa-skill.jar"