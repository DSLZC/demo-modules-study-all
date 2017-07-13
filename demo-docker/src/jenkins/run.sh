docker run -d -m 1000m --name jenkins -p 1002:80 --restart=always \
    -v $PWD/maven_repository/:/opt/apache-maven-3.5.0/repository/ \
    -v $PWD/docker-volume/:/opt/jenkins \
    dsl/jenkins:0.1

