FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/producer-*.jar producer.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/producer.jar"]
