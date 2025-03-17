FROM openjdk:17-jdk-slim

# Set non-interactive mode to avoid prompts
ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y xvfb


WORKDIR /app
COPY Lab3.jar /app/app.jar

CMD ["sh", "-c", "Xvfb :99 -screen 0 1024x768x16 & java -jar /app/app.jar"]

