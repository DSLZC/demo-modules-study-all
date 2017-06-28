#!/bin/bash

docker start gitlab-postgresql gitlab-redis gitlab

docker ps
docker logs -f gitlab