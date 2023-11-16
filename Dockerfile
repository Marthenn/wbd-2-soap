FROM maven:3.9.2-amazoncorretto-8
WORKDIR /app
COPY . .
RUN mvn clean install
# CMD ["mvn", "clean", "install"]
ENTRYPOINT ["java", "-jar", "./target/webwbd-jar-with-dependencies.jar"]
EXPOSE 50435