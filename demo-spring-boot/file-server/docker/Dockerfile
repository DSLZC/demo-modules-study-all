FROM frolvlad/alpine-oraclejdk8:slim
MAINTAINER dongsilin "dslzc@foxmail.com"

RUN mkdir /data/upload -p
WORKDIR /data

EXPOSE 1029

VOLUME ['/data/upload']

COPY app.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Duser.timezone=GMT+8","-jar","app.jar", "-Xmx200m", "-Xms200m"]

