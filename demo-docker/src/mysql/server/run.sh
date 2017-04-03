root_user_password=123456

docker run -d -m 500m -p 3306:3306 -e MYSQL_ROOT_PASSWORD=${root_user_password} -e TZ=Asia/Shanghai \
-v $PWD/data/:/var/lib/mysql/ \
--name mysql-container mysql --character-set-server=utf8 --collation-server=utf8_unicode_ci

docker logs -f mysql-container
