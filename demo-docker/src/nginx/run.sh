#!/bin/bash

docker run -d -p 80:80 -p 443:443 -m 1000m --name nginx --restart=always \
    -v $PWD/html:/usr/share/nginx/html \
    -v $PWD/conf:/etc/nginx/my_conf \
    -v $PWD/logs:/var/log/nginx \
    nginx nginx -g "daemon off;" -c /etc/nginx/my_conf/nginx.conf

 docker ps