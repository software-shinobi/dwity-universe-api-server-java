
## the first stage of our build will use a maven 3.6.1 parent image

FROM maven:3.8.7-openjdk-18-slim AS MAVEN_BUILD

## copy the pom and src code to the container

COPY ./ ./

## package our application code

RUN mvn clean package

## see what files are available 

# RUN ls /target/

# RUN find / | grep .properties

## the second stage of our build will use open jdk 8 on alpine 3.9

FROM eclipse-temurin:18-jre-alpine

RUN apk add py3-pip

## copy only the artifacts we need from the first stage and discard the rest

COPY --from=MAVEN_BUILD /target/text-format-dashboard-online-api-4.44.jar /text-format-dashboard-online-api.jar

COPY --from=MAVEN_BUILD /src/main/resources/application.properties /application.properties
# COPY --from=MAVEN_BUILD /src/test/resources/conf/database.properties /conf/database.properties

# set the startup command to execute the jar

CMD ["java", "-jar", "/text-format-dashboard-online-api.jar"]
