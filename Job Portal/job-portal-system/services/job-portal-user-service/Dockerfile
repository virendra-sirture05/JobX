# ---------- Stage 1 : Build ----------
FROM maven:3.9.11-eclipse-temurin-24 AS builder

WORKDIR /build

# Copy Maven wrapper and build files
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .

RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy application source
COPY src ./src

# Build application
RUN ./mvnw clean package -DskipTests



# ---------- Stage 2 : Runtime ----------
FROM eclipse-temurin:24-jre

LABEL maintainer="Ritesh Patil"
LABEL application="JobX Backend"

WORKDIR /app

# Install only what is required for HEALTHCHECK
RUN apt-get update && \
    apt-get install -y --no-install-recommends wget && \
    rm -rf /var/lib/apt/lists/*

# Create non-root user
RUN groupadd --system spring && \
    useradd --system --gid spring spring

# Copy application
COPY --from=builder /build/target/*.jar app.jar

# Give ownership
RUN chown spring:spring app.jar

# Switch to non-root user
USER spring
ENV HOME=/home/spring
EXPOSE 8080

HEALTHCHECK --interval=30s \
             --timeout=5s \
             --start-period=40s \
             --retries=3 \
CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", \
            "-XX:+UseContainerSupport", \
            "-XX:MaxRAMPercentage=75.0", \
            "-XX:+ExitOnOutOfMemoryError", \
            "-jar", \
            "app.jar"]