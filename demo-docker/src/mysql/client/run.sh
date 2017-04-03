mysql_container_name=mysql-container

docker run -it --link ${mysql_container_name}:mysql --rm mysql \
sh -c 'exec mysql -h"$MYSQL_PORT_3306_TCP_ADDR" -P"$MYSQL_PORT_3306_TCP_PORT" -uroot -p"$MYSQL_ENV_MYSQL_ROOT_PASSWORD" --default-character-set=utf8'
