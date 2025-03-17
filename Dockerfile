FROM openjdk:17-jdk-slim

# Fix APT issues and install missing GUI dependencies
RUN apt-get update && apt-get install -y --no-install-recommends \
    libxrender1 libxtst6 libxi6 libxext6 libfreetype6 && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY Lab3.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]
