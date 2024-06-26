# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 AS meeting_management_builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean -DskipTests
RUN mvn clean package -DskipTests
RUN mvn clean install -DskipTests

# Stage 2: Create a minimal JRE image
FROM openjdk:17-jdk-alpine
WORKDIR /app
ARG JAR_FILE=/app/target/meeting-management-application.jar
COPY --from=meeting_management_builder /app/target/meeting-management-application.jar meeting-management-application.jar

EXPOSE 8080

# Specify the default command to run on startup
CMD ["java", "-jar", "meeting-management-application.jar"]