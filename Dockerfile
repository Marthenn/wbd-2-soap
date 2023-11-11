FROM maven:3.9.2-amazoncorretto-8
WORKDIR /app
COPY . .
EXPOSE 8010
RUN mvn clean install
# ENTRYPOINT ["java", '-jar', 'target/Java-0.0.1-SNAPSHOT.jar']