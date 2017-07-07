#!/bin/bash

# Step 1. Launch a postgresql container
docker run --name redmine_postgresql -d -m 200m --restart=always \
    -e 'DB_NAME=redmine_production' \
    -e 'DB_USER=redmine' -e 'DB_PASS=123456' \
    -v /data/redmine/postgresql/docker-volume:/var/lib/postgresql \
    registry.cn-hangzhou.aliyuncs.com/acs-sample/postgresql-sameersbn

# Step 2. Launch the redmine container
docker run --name redmine -d -m 2048m --restart=always \
    --link redmine_postgresql:postgresql \
    -p 1006:3000 \
    -v /etc/localtime:/etc/localtime \
    -v /data/redmine/docker-volume:/usr/src/redmine/files \
    redmine

# 默认用户：admin admin

docker ps
docker logs -f redmine