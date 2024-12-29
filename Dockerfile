FROM openjdk:17-jdk-slim

COPY target/groceryBooking-0.0.1-SNAPSHOT.jar groceryBooking.jar

ENTRYPOINT ["java", "-jar", "groceryBooking.jar"]
EXPOSE 8080
