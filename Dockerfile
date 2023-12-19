
## the first stage of our build will use a maven 3.6.1 parent image

FROM maven:3.8.7-openjdk-18-slim AS MAVEN_BUILD

MAINTAINER Software Shinobi "the.software.shinobi@gmail.com"

## define key variables

ARG JAR_FILE_NAME dwity-universe-api-1.0.jar

WORKDIR /

## copy the pom and src code to the container

COPY ./ ./

## package our application code

RUN mvn install

## the second stage of our build will use open jdk 8 on alpine 3.9

FROM eclipse-temurin:18-jre-alpine

## copy only the artifacts we need from the first stage and discard the rest

COPY --from=MAVEN_BUILD /target/$JAR_FILE_NAME /$JAR_FILE_NAME

COPY --from=MAVEN_BUILD /src/main/resources/application.properties /application.properties

CMD ["java", "-jar", "/dwity-universe-api-1.0.jar"] 
