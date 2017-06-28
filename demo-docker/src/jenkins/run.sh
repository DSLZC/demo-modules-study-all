docker run -d -m 1000m --name jenkins -p 8080:80 \
-v $PWD/maven_repository/:/opt/apache-maven-3.5.0/repository/ \
-v $PWD/maven_settings.xml:/opt/apache-maven-3.5.0/conf/settings.xml \
-v $PWD/docker-volume/:/opt/jenkins \
dsl/jenkins:0.1

