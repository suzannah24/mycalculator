FROM openjdk:17-jdk-slim

# Install missing GUI dependencies
RUN apt-get update && apt-get install -y \
    libxrender1 libxtst6 libxi6 libxext6 libfreetype6 && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY Lab3.jar /app/Lab3.jar
CMD ["java", "-jar", "/app/Lab3.jar"]
