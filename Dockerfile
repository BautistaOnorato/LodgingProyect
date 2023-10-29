FROM openjdk:20-jdk
WORKDIR /opt
ENV PORT 9090
EXPOSE 9090
COPY target/bookingApp-0.0.1-SNAPSHOT.jar  /opt/bookingApp.jar
ENTRYPOINT exec java $JAVA_OPTS -jar bookingApp.jar