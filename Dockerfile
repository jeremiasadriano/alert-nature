FROM ubuntu:22.04 as ubuntu
RUN apt-get update -y &&  \
        apt-get install openjdk-17-jdk -y && \
        apt-get install maven -y
COPY . .
RUN mvn clean install -DskipTests

FROM openjdk:17-alpine AS java

EXPOSE 8080

RUN mkdir -p app

COPY --from=ubuntu /target/*.jar /app/alertNature.jar

ENTRYPOINT ["java", "-jar", "/app/alertNature.jar"]