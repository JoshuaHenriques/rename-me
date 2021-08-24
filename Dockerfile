FROM openjdk:11
LABEL maintainer="github@joshuahenriques"
VOLUME /app
ADD build/libs/me-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]