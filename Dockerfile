FROM amazonlinux:latest
RUN yum install java-17-amazon-corretto -y
RUN yum install maven -y 
WORKDIR /app 
MAINTAINER  Pooja
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java","-jar","/app/app.jar"]
RUN java -version
