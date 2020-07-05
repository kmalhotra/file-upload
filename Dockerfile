# Stage 0
FROM maven:3.6.0-jdk-11-slim
COPY . /file-upload/api

WORKDIR "/file-upload/api"
RUN mvn clean package

FROM openjdk:8-jdk-alpine

WORKDIR /file-upload
COPY --from=0 /file-upload/api/target/file-upload-1.0.jar api.jar

ENTRYPOINT ["java","-jar","api.jar"]
EXPOSE 8080