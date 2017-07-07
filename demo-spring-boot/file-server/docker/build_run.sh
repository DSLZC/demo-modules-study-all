#!/bin/bash

#parameter
server_port=1029
app_images_name=dsl/file_server:0.1
app_container_name=file_server
app_data_root_dir=/data/workspace/file_server

app_logs_dir=${app_data_root_dir}/logs
app_volume_dir=${app_data_root_dir}/docker-volume

#如果文件夹不存在，创建文件夹
if [ ! -d "$app_logs_dir" ]; then
  mkdir -p $app_logs_dir;
fi

if [ ! -d "$app_volume_dir" ]; then
  mkdir -p $app_volume_dir;
fi

#remove
docker stop ${app_container_name}
docker rm ${app_container_name}

#build
docker build -t ${app_images_name} .

#start
docker run -d -m 1000m --name ${app_container_name} --restart=always \
 -e SPRING_PROFILES_ACTIVE=linux \
 -v ${app_logs_dir}:/data/logs \
 -v ${app_volume_dir}:/data/upload \
 -p ${server_port}:${server_port} \
 ${app_images_name}

docker ps
docker logs --tail 0 -f ${app_container_name}
