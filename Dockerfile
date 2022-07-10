#
# This dockerfile expects a compiled artifact in the target folder.
# Call "mvn clean package" first!
#
FROM tomcat:9.0.64-jre17

EXPOSE 8080
# HEALTHCHECK --interval=10s CMD curl --fail http://localhost:8080/health || exit 1


COPY WebApp/target/cleanuri-webapp-*.war /usr/local/tomcat/webapps/ROOT.war
