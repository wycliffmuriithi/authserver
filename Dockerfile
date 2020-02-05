# Alpine Linux with OpenJDK JRE
#FROM openjdk:8-jre-alpine

# copy WAR into image
#COPY sme-1.0-SNAPSHOT-fat.jar /app.jar
# local config directory on windows
#ENV CONFIG_DIR "C:\Users\wgicheru\eclipse-workspace\sme"
# run application with this command line
#CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/app.jar"]



FROM maven:3.5.2-jdk-8 AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package

FROM openjdk:8-jre-alpine
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/idsauth-0.0.1-SNAPSHOT.jar /app.jar

#ENV CONFIG_DIR "C:\Users\wgicheru\eclipse-workspace\sme"

CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/app.jar"]