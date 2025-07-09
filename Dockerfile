FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /workspace

COPY pom.xml ./

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
EXPOSE 8080

COPY --from=builder /workspace/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]