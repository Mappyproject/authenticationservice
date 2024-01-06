FROM openjdk:17
EXPOSE 8083
ADD target/authservice-0.0.1-SNAPSHOT.jar authservice.jar
ENTRYPOINT ["java", "-jar", "/authservice.jar"]