FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install -y xvfb
WORKDIR /app
COPY Lab3.jar /app/Lab3.jar
CMD ["sh", "-c", "xvfb-run java -jar /app/Lab3.jar"]
