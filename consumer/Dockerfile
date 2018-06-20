FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY consumer/target/consumer-*.jar consumer.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/consumer.jar"]
