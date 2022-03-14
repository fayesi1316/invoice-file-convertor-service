FROM openjdk:11

ENV PORT 8080
ENV HOST 0.0.0.0

ADD target/invoice-file-convertor-service-1.0-SNAPSHOT.jar invoice-file-convertor-service-1.0-SNAPSHOT.jar

ADD config.yml config.yml

CMD java -jar invoice-file-convertor-service-1.0-SNAPSHOT.jar server config.yml

EXPOSE 8080