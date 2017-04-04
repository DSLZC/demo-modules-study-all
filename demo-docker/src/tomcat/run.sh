docker run -d -m 500m \
-v $PWD/webapps/:/usr/local/tomcat/webapps/:rw \
-v $PWD/logs/:/usr/local/tomcat/logs/:rw \
-v /etc/localtime:/etc/localtime:rw \
-p 8080:8080 --name tomcat-container docker.io/tomcat:8.5-jre8-alpine

docker logs --tail 0 -f tomcat-container
