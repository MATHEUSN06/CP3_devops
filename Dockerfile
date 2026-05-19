
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre-jammy
WORKDIR /app


RUN useradd -m matheus
COPY --from=build /app/target/*.jar app.jar


ENV SPRING_PROFILES_ACTIVE=prod


RUN chown -R matheus:matheus /app
USER matheus

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]