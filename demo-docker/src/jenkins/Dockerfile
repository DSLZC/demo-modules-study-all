FROM ubuntu:16.04
MAINTAINER  dongsilin dslzc@foxmail.com

# 安装git
COPY apt-get-sources.list /etc/apt/sources.list
RUN apt-get update -y && apt-get install git -y
# RUN git config --global user.name "root" && git config --global user.email "dslzc@foxmail.com"

# 创建jenkins文件存放目录
RUN mkdir /opt/jenkins -p

# 上传包
ADD jdk-8u131.gz /opt/
ADD maven-3.5.0.tar.gz /opt/
COPY jenkins.war /opt/jenkins.war

# 环境变量配置
ENV JAVA_HOME /opt/jdk1.8.0_131
ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV MAVEN_HOME /opt/apache-maven-3.5.0
ENV JENKINS_HOME /opt/jenkins
ENV PATH $PATH:$JAVA_HOME/bin:$MAVEN_HOME/bin:$JENKINS_HOME

# maven setting.xml
COPY maven_settings.xml /opt/apache-maven-3.5.0/conf/settings.xml

# 设置工作目录
WORKDIR /opt/

EXPOSE 80

# 运行jenkins的war包
ENTRYPOINT ["java", "-Duser.timezone=GMT+8", "-jar", "jenkins.war", "--httpPort=80"]

