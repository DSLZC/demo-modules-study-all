#!/bin/bash

docker start nexus

docker ps
docker logs --tail 0 -f nexus