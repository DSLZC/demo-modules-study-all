#!/bin/bash

docker start $(docker ps -a | awk '{print $1}' | tail -n +2)