#parameter
server_port=8006
profile_name=debug
app_images_name=platform/h5_wx-image:0.1
app_container_name=h5_wx-container
app_data_root_dir=/data/docker-volume/mall-mobile-h5_wx

app_logs_dir=${app_data_root_dir}/logs
app_upload_dir=${app_data_root_dir}/upload
#如果文件夹不存在，创建文件夹
if [ ! -d "$app_logs_dir" ]; then
  mkdir -p $app_logs_dir;
fi

if [ ! -d "$app_upload_dir" ]; then
  mkdir -p $app_upload_dir;
fi

#remove
docker stop ${app_container_name}
docker rm ${app_container_name}
docker rmi ${app_images_name}

#build
docker build -t ${app_images_name} .

#start
docker run -d -m 1500m --name ${app_container_name} \
 -v ${app_logs_dir}/:/logs/:rw \
 -v ${app_upload_dir}/:/upload/:rw \
 -e "SPRING_PROFILES_ACTIVE=${profile_name}" \
 -p ${server_port}:${server_port} \
 -t ${app_images_name}

docker ps
docker logs --tail 0 -f ${app_container_name}
