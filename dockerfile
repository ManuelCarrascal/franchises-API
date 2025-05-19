FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/franchise-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENV DB_URL=${DB_URL:-r2dbc:mysql://localhost:3307/franchises}
ENV DB_USERNAME=${DB_USERNAME:-root}
ENV DB_PASSWORD=${DB_PASSWORD:-root}

ENTRYPOINT ["java", "-jar", "app.jar"]