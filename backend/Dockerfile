# ---------- Stage 1 : Build ----------
FROM maven:3.9.11-eclipse-temurin-24 AS builder

WORKDIR /app

# Copy Maven files first for dependency caching
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .

RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build application
RUN ./mvnw clean package -DskipTests


# ---------- Stage 2 : Runtime ----------
FROM eclipse-temurin:24-jre
RUN apt-get update && \
    apt-get install -y wget && \
    rm -rf /var/lib/apt/lists/*
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
HEALTHCHECK --interval=30s \
             --timeout=5s \
             --start-period=40s \
             --retries=3 \
CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java","-jar","app.jar"]