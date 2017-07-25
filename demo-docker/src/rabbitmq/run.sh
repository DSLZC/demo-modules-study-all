#!/bin/bash

docker run -d -h rabbitmq1 -m 2000m -p 5672:5672 -p 1080:15672 --name rabbitmq \
    -e RABBITMQ_VM_MEMORY_HIGH_WATERMARK=80% \
    -e RABBITMQ_DEFAULT_USER=root \
    -e RABBITMQ_DEFAULT_PASS=123456789 \
    -v $PWD/docker-volume:/var/lib/rabbitmq \
    rabbitmq:management
    # -v $PWD/rabbitmq.config:/etc/rabbitmq/rabbitmq.config \

 docker ps