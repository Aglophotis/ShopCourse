FROM maven:3.6.0-jdk-11-slim AS build
COPY identity/src /home/app/src
COPY identity/pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package


FROM openjdk:8-jre-slim
COPY --from=build /home/app/target/identity-1.0-SNAPSHOT.jar /usr/local/lib/app.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]