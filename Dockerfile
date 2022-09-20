FROM openjdk:8
VOLUME /tmp
ADD ./target/TestTask-1.0-SNAPSHOT.jar TestTask-1.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","TestTask-1.0-SNAPSHOT.jar"]