FROM openjdk:11
COPY target/jim-1.0-SNAPSHOT.jar /
CMD ["java", "-jar", "jim-1.0-SNAPSHOT.jar"]