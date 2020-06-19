# # Build stage
FROM maven:3.6.3 as build

COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package

# # Package stage
FROM openjdk:11-jdk
COPY --from=build /home/app/target/crypto-1.0-SNAPSHOT.jar /usr/local/lib/api-user-1.0-SNAPSHOT.jar
COPY swagger.json /usr/local/lib/
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/crypto-1.0-SNAPSHOT.jar"]