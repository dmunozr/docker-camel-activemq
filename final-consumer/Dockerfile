FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/final-consumer-*.jar final-consumer.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/final-consumer.jar"]
