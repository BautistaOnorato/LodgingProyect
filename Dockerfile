FROM amazoncorretto:20-alpine-jdk
EXPOSE 8080
COPY target/bookingApp-0.0.1-SNAPSHOT.jar  app.jar
ENV DB_HOST bjiwgii26ti6lhkxncrd-mysql.services.clever-cloud.com
ENV DB_NAME bjiwgii26ti6lhkxncrd
ENV DB_PASSWORD iJ3aJR98ME771CGOuoEv
ENV DB_PORT 3306
ENV DB_USER u6r9g3vnweca6gen
ENTRYPOINT ["java", "-jar", "/app.jar"]
