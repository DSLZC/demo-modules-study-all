#!/bin/bash
docker start elasticsearch
docker ps
docker logs --tail 0 -f elasticsearch