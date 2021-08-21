FROM maven:3.8.1-openjdk-16 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM gcr.io/distroless/java:11
COPY --from=build /usr/src/app/target/profile-service-0.0.1-SNAPSHOT.jar /usr/app/profile-service-0.0.1-SNAPSHOT.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/usr/app/profile-service-0.0.1-SNAPSHOT.jar"]  