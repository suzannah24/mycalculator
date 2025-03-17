FROM openjdk:17-jdk-slim
WORKDIR /app
COPY Lab3.jar app.jar
CMD ["java", "-jar", "Lab3.jar"]
