FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/consumer-*.jar consumer.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/consumer.jar"]
