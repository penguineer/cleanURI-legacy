FROM maven:3-jdk-11-openj9 AS build

COPY WebApp/ /app
WORKDIR app
RUN mvn package


FROM tomcat:9-jdk11-openjdk

EXPOSE 8080

ENV APP_FILE cleanuri-webapp-0.0.3-SNAPSHOT.war

COPY --from=build /app/target/$APP_FILE /usr/local/tomcat/webapps/ROOT.war

