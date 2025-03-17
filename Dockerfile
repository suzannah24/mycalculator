FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/*.jar app.jar
CMD ["java", "-jar", "Lab3.jar"]
