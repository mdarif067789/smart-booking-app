# Build stage
FROM --platform=linux/amd64 maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM --platform=linux/amd64 openjdk:17-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/booking-0.0.1-SNAPSHOT.jar booking.jar

EXPOSE 8080
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar booking.jar"]
