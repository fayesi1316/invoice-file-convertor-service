FROM maven:3.6.0-jdk-11-slim AS build

ENV PORT 8080
ENV HOST 0.0.0.0

RUN mvn -f ./pom.xml clean package

ADD target/invoice-file-convertor-service-1.0-SNAPSHOT.jar invoice-file-convertor-service-1.0-SNAPSHOT.jar

ADD config.yml config.yml

CMD java -jar invoice-file-convertor-service-1.0-SNAPSHOT.jar server config.yml

EXPOSE 8080
