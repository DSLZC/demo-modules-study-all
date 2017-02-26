#!/bin/bash

test_ip=172.31.1.80
mvn clean package -Dmaven.test.skip=true
scp target/*.jar root@$test_ip:/data/workspace/dsl-file-server/app.jar