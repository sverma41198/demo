
FROM openjdk:17-alpine
# Set the working directory in the container
WORKDIR /app
# Copy the Spring Boot application JAR file to the container
COPY ./target/boot03onlineshopping-0.0.1-SNAPSHOT.jar .
CMD ["java","-jar","boot03onlineshopping-0.0.1-SNAPSHOT.jar"]