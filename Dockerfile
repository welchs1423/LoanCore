FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM tomcat:9.0-jdk21
COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/LoanCore.war
RUN mkdir -p /usr/local/tomcat/upload/loan_docs/
EXPOSE 8080
CMD ["catalina.sh", "run"]