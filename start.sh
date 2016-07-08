#!/bin/bash

REMOTE_TARGET=$1    ##服务器项目存储目录
REMOTE_VERTX_ID=$2   ##启动服务时的 my-app-name
REMOTE_CONF_PATH=$3      ##配置文件路径
REMOTE_VERTICLE=$4     ##verticle
jarName=$5

if ! which mysql; then
    yum install -y mysql
fi

if ! which redis-server; then
    yum install -y redis
fi

if ! which npm; then
    yum install -y npm
fi

if [ `ls ~/node_modules/ | wc -l` == 0 ]; then
    npm install vertx3-full
fi

/root/node_modules/vertx3-full/vertx/bin/vertx run $REMOTE_VERTICLE -cp $REMOTE_TARGET/$jarName -Dvertx.clusterManagerFactory=io.vertx.spi.cluster.impl.hazelcast.HazelcastClusterManagerFactory -Dhttps.path=$REMOTE_TARGET/$REMOTE_CONF_PATH -Dvertx-id=$REMOTE_VERTX_ID &
