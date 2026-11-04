FROM eclipse-temurin:25-jdk AS builder

WORKDIR /app

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Copy complete project
COPY . .

WORKDIR /app/cloud/job-portal-config-server

# Build config server
RUN mvn clean package

FROM eclipse-temurin:25-jdk

WORKDIR /app

# Install curl for healthchecks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

COPY --from=builder /app/cloud/job-portal-config-server/target/*.jar app.jar

EXPOSE 8888

HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8888/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
