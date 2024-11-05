FROM gradle:8.10.2-jdk21 AS build
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts ./
RUN gradle build --no-daemon || true
COPY . .
RUN gradle build --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar vkbot.jar
RUN adduser -D -h /app appuser
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "vkbot.jar"]
