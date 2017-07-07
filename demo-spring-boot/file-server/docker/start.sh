#!/bin/bash

docker start file_server

docker ps
docker logs -f file_server