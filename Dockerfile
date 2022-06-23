FROM amazoncorretto:17.0.3-alpine3.15@sha256:c3445d61df8a764cf4f2645a2a9ab92cfe1ace5864e1210efebef3956db49b97 AS build
COPY . /source
WORKDIR /source
ARG GITHUBPACKAGESUSERNAME
ARG GITHUBPACKAGESPASSWORD
ENV ORG_GRADLE_PROJECT_GitHubPackagesUsername=$GITHUBPACKAGESUSERNAME
ENV ORG_GRADLE_PROJECT_GitHubPackagesPassword=$GITHUBPACKAGESPASSWORD
RUN ./gradlew shadowJar

FROM tomcat:jre17@sha256:0ef2c399d5ae345f52bcb8b149cb943f3ae96ec41384a78d9bc5a5a364bbce41
COPY --from=build /source/build/libs/*.jar /usr/local/tomcat/webapps/mfp-alexa-skill.jar

CMD "catalina.sh" "run"