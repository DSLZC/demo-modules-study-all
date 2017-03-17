#!/bin/bash

ip=114.115.217.231
mvn clean package -Dmaven.test.skip=true
scp target/*.jar root@$ip:/data/workspace/file-server/app.jar