


Step 1. Launch a postgresql container
docker run --name gitlab-postgresql -d \
    --env 'DB_NAME=gitlabhq_production' \
    --env 'DB_EXTENSION=pg_trgm' \
    --env 'DB_USER=gitlab' --env 'DB_PASS=123456' \
    --volume /data/gitlab/postgresql/docker-volume:/var/lib/postgresql \
    registry.cn-hangzhou.aliyuncs.com/acs-sample/postgresql-sameersbn

Step 2. Launch a redis container
docker run --name gitlab-redis -d \
    --volume /data/gitlab/redis/docker-volume:/var/lib/redis \
    registry.cn-hangzhou.aliyuncs.com/acs-sample/redis-sameersbn

Step 3. Launch the gitlab container
docker run --name gitlab -d \
    --link gitlab-postgresql:postgresql \
    --link gitlab-redis:redisio \
    --publish 10022:22 \
    --publish 10080:80 \
    --env 'GITLAB_PORT=10080' \
    --env 'GITLAB_SSH_PORT=10022' \
    --env 'GITLAB_SECRETS_DB_KEY_BASE=long-and-random-alpha-numeric-string' \
    --env 'GITLAB_SECRETS_SECRET_KEY_BASE=long-and-random-alphanumeric-string' \
    --env 'GITLAB_SECRETS_OTP_KEY_BASE=long-and-random-alpha-numeric-string' \
    --env 'GITLAB_BACKUPS=daily' \
    --volume /data/gitlab/docker-volume:/home/git/data \
    registry.cn-hangzhou.aliyuncs.com/acs-sample/gitlab-sameersbn
