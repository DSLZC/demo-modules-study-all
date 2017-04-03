
FROM frolvlad/alpine-oraclejdk8:slim
MAINTAINER dongsilin "dslzc@foxmail.com"

ADD app.jar app.jar
RUN sh -c 'touch app.jar'

EXPOSE 8006

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Duser.timezone=GMT+8","-jar","app.jar"]

