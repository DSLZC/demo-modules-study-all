#!/bin/bash

docker run -d -p 9200:9200 -p 9300:9300 --name elasticsearch --restart=always \
    -v $PWD/docker-volume:/usr/share/elasticsearch/data \
    -v $PWD/logs:/usr/share/elasticsearch/logs \
    -v $PWD/analysis-ik:/usr/share/elasticsearch/plugins/ik \
    -v $PWD/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
    -e TZ=Asia/Shanghai \
    elasticsearch:2.4.6

docker ps
docker logs --tail 0 -f elasticsearch