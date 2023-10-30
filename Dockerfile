FROM amazoncorretto:20-alpine-jdk
EXPOSE 9090
COPY target/bookingApp-0.0.1-SNAPSHOT.jar  app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]