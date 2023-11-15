FROM amazoncorretto:20-alpine-jdk
EXPOSE 8080
COPY target/bookingApp-0.0.1-SNAPSHOT.jar  app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]