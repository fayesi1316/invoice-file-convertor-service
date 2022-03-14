FROM maven:3.6.0-jdk-11-slim AS build

ENV PORT 8080
ENV HOST 0.0.0.0

## Build stage#
COPY src /home/app/src
COPY pom.xml /home/app
COPY config.yml /home/app
RUN mvn -f /home/app/pom.xml clean package

## Package stage#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/invoice-file-convertor-service-1.0-SNAPSHOT.jar /home/app/target/invoice-file-convertor-service-1.0-SNAPSHOT.jar
COPY --from=build /home/app/config.yml /home/app/target/config.yml
EXPOSE 8080

ENTRYPOINT ["java","-jar","/home/app/target/invoice-file-convertor-service-1.0-SNAPSHOT.jar", "server", "/home/app/target/config.yml"]
