FROM openjdk:17-jdk-slim

# Install missing GUI dependencies
RUN apt-get update && apt-get install -y --no-install-recommends \
    libxrender1 libxtst6 libxi6 libxext6 libfreetype6 && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY Lab3.jar /app/app.jar

# Expose a port (if needed)
EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]

