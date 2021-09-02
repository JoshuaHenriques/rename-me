FROM openjdk:11
LABEL maintainer="github@joshuahenriques"
VOLUME /app
ADD build/libs/bootstrap-1.0.1.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app.jar"]