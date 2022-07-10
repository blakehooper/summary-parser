FROM openjdk:18.0.1.1-jdk
VOLUME /tmp
COPY *.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]