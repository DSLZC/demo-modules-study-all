docker run -d -m 200m -p 6379:6379 \
-v $PWD/redis.conf:/usr/local/etc/redis/redis.conf \
-v $PWD/data/:/data/redis/ \
--name redis redis \
redis-server /usr/local/etc/redis/redis.conf
