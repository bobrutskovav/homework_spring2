FROM java:8
VOLUME /tmp
WORKDIR /opt
COPY /library.jar /opt
EXPOSE 8080 8080
ENTRYPOINT ["java","-jar","/opt/library.jar"]