FROM gradle:jdk11-jammy AS builder
WORKDIR /app/api-jwt
COPY settings.gradle .
COPY build.gradle .
COPY src src
RUN gradle clean assemble

FROM amazoncorretto:11-alpine
RUN apk update && apk upgrade
WORKDIR /app
COPY --from=builder /app/api-jwt/build/libs/api-jwt.jar .
ENTRYPOINT ["java", "-jar", "api-jwt.jar"]
