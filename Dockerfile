# Build the website with Maven, then run it on Tomcat
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q package -DskipTests

FROM tomcat:9.0-jdk17
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/wedding-1.0.war /usr/local/tomcat/webapps/ROOT.war
COPY data /data
ENV JAVA_OPTS="-Dwd44.data=/data"
EXPOSE 8080
CMD ["catalina.sh", "run"]
