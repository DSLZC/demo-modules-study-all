#!/bin/bash

chown -R 200 /data/nexus/docker-volume

docker run -d -p 1004:8081 -m 1000m --name nexus --restart=always \
    -e CONTEXT_PATH=/ \
    -e SONATYPE_WORK=/opt/sonatype/nexus_run \
    -v /data/nexus/docker-volume:/opt/sonatype/nexus_run \
    sonatype/nexus

# 默认用户：admin admin123
 docker ps
 docker logs --tail 0 -f nexus
