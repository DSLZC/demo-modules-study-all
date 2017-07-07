#!/bin/bash

ip=172.31.1.70
mvn clean package -Dmaven.test.skip=true
scp target/*.jar root@$ip:/data/workspace/file_server/app.jar