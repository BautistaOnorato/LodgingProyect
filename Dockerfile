FROM openjdk:20-jdk
WORKDIR /opt
ENV DB_URL="jdbc:mysql://aws.connect.psdb.cloud/ecommerce-admin?sslMode=VERIFY_IDENTITY"
ENV DB_USERNAME="jg4qd9flyyn8obp3dwpo"
ENV DB_PASSWORD="pscale_pw_3lQ6jTRD2fYky6Z9RQ2mYJSI9QBYZxC660tbU3anHoe"
EXPOSE 9090
COPY target/bookingApp-0.0.1-SNAPSHOT.jar  /opt/bookingApp.jar
ENTRYPOINT exec java $JAVA_OPTS -jar bookingApp.jar