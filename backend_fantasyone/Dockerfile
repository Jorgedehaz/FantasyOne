# Stage 1: build
FROM maven:3.9.1-eclipse-temurin-17 AS build
WORKDIR /app
# Copiamos sólo pom para cache de dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el código y empaquetamos
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 2: runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copiamos el jar generado
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
