FROM maven:3.9.4-amazoncorretto-20-al2023 AS build
WORKDIR /app
COPY . .
RUN mvn clean package
FROM amazoncorretto:20-al2023-generic
EXPOSE 9090
COPY --from=build /app/target/bookingApp-0.0.1-SNAPSHOT.jar /app/bookingApp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/bookingApp-0.0.1-SNAPSHOT.jar"]