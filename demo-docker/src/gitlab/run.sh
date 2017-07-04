#!/bin/bash

# Step 1. Launch a postgresql container
docker run --name gitlab-postgresql -d -m 200m --restart=always \
    -e 'DB_NAME=gitlabhq_production' \
    -e 'DB_EXTENSION=pg_trgm' \
    -e 'DB_USER=gitlab' -e 'DB_PASS=123456' \
    -v /data/gitlab/postgresql/docker-volume:/var/lib/postgresql \
    registry.cn-hangzhou.aliyuncs.com/acs-sample/postgresql-sameersbn

# Step 2. Launch a redis container
docker run --name gitlab-redis -d -m 50m --restart=always \
    -v /data/gitlab/redis/docker-volume:/var/lib/redis \
    registry.cn-hangzhou.aliyuncs.com/acs-sample/redis-sameersbn

# Step 3. Launch the gitlab container
docker run --name gitlab -d -m 2048m --restart=always \
    --link gitlab-postgresql:postgresql \
    --link gitlab-redis:redisio \
    -p 1001:22 \
    -p 1000:80 \
    -e 'DEBUG=false' \
    -e 'TZ=Asia/Beijing' \
    -e 'GITLAB_TIMEZONE=Beijing' \
    -e 'GITLAB_SECRETS_DB_KEY_BASE=long-and-random-alphanumeric-string' \
    -e 'GITLAB_SECRETS_SECRET_KEY_BASE=long-and-random-alphanumeric-string' \
    -e 'GITLAB_SECRETS_OTP_KEY_BASE=long-and-random-alphanumeric-string' \
    -e 'GITLAB_ROOT_PASSWORD=123456789' \
    -e 'GITLAB_ROOT_EMAIL=dslzc@foxmail.com' \
    -e 'GITLAB_HOST=172.31.1.61' \
    -e 'GITLAB_PORT=1000' \
    -e 'GITLAB_SSH_PORT=1001' \
    -e 'GITLAB_RELATIVE_URL_ROOT=' \
    -e 'GITLAB_NOTIFY_ON_BROKEN_BUILDS=true' \
    -e 'GITLAB_NOTIFY_PUSHER=false' \
    -e 'GITLAB_EMAIL=dslzc@foxmail.com' \
    -e 'GITLAB_EMAIL_REPLY_TO=798171101@qq.com' \
    -e 'GITLAB_BACKUP_SCHEDULE=daily' \
    -e 'GITLAB_BACKUP_EXPIRY=7776000' \
    -e 'GITLAB_BACKUP_TIME=05:00' \
    -e 'NGINX_MAX_UPLOAD_SIZE=60m' \
    -e 'SMTP_ENABLED=true' \
    -e 'SMTP_DOMAIN=mail.51ishare.com' \
    -e 'SMTP_HOST=mail.51ishare.com' \
    -e 'SMTP_PORT=25' \
    -e 'SMTP_USER=gitlab@51ishare.com' \
    -e 'SMTP_PASS=12345678' \
    -v /data/gitlab/docker-volume:/home/git/data \
    -v /data/gitlab/log:/var/log/gitlab \
    registry.cn-hangzhou.aliyuncs.com/acs-sample/gitlab-sameersbn

# 默认用户：root 123456789

docker ps
docker logs -f gitlab