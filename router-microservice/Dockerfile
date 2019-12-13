FROM maven:3.6.0-jdk-11-slim AS build
COPY router-microservice/src /home/app/src
COPY router-microservice/pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package


FROM openjdk:8-jre-slim
COPY --from=build /home/app/target/router-1.0-SNAPSHOT.jar /usr/local/lib/app.jar
EXPOSE 8111
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]