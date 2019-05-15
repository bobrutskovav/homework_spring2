FROM java:8
VOLUME /tmp
WORKDIR /opt
COPY /target/spring_homework2-1.0-SNAPSHOT.jar /opt
EXPOSE 8080 8080
ENTRYPOINT ["java","-jar","/opt/spring_homework2-1.0-SNAPSHOT.jar"]