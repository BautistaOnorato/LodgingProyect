FROM amazoncorretto:21-alpine-jdk
MAINTAINER bookingApp
COPY target/bookingApp-0.0.1-SNAPSHOT.jar  bookingApp.jar
ENTRYPOINT ["java","-jar","/bookingApp.jar"]