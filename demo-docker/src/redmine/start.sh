#!/bin/bash

docker start redmine redmine_postgresql

docker ps
docker logs -f redmine