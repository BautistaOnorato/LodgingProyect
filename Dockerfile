FROM openjdk:20-jdk
WORKDIR /opt
EXPOSE 9090
COPY target/bookingApp-0.0.1-SNAPSHOT.jar  /opt/bookingApp.jar
ENTRYPOINT exec java $JAVA_OPTS -jar bookingApp.jar