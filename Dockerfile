FROM adoptopenjdk/openjdk11:alpine-jre
COPY . /app
ENTRYPOINT ["java","-jar","/app/target/kanban-0.0.1-SNAPSHOT.jar"]