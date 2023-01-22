FROM gradle:7.2.0-jdk17 as build
COPY . /app
WORKDIR /app
RUN gradle build --no-daemon

FROM openjdk:17-alpine
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]