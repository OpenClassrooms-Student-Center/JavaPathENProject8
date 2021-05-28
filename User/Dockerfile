FROM openjdk:8

EXPOSE 8080

ARG JAR_FILE=./build/libs/user-1.0.0.jar

WORKDIR /app/tour_guide

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar", "app.jar"]