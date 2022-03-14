FROM maven:3.6.0-jdk-11-slim AS build

ENV PORT 8080
ENV HOST 0.0.0.0

COPY src /home/app/src
COPY config.yml /home/app
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

## Package stage#
FROM openjdk:11-jre-slim

COPY --from=build /home/app/target/invoice-file-convertor-service-1.0-SNAPSHOT.jar /usr/local/lib/invoice-file-convertor-service-1.0-SNAPSHOT.jar
COPY --from=build /home/app/config.yml /usr/local/lib/config.yml

CMD java -jar invoice-file-convertor-service-1.0-SNAPSHOT.jar server config.yml

EXPOSE 8080



