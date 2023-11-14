FROM maven:3.9.2-amazoncorretto-8
WORKDIR /app
COPY . .
EXPOSE 50435
RUN mvn clean install
# RUN mvn exec:java
ENTRYPOINT ["java", '-jar', './target/webwbd-jar-with-dependencies.jar']