#!/bin/bash

DATE=$(date +%Y%m%d)
DIR="/web/gw"
APPLICATION_NAME="base-service"
APPLICATION_FINAL_NAME="gw-base-service-test-"${DATE}
APPLICATION_LOG="/var/log/gw/baseService/"

cd ${DIR}
tar -xvf "${APPLICATION_FINAL_NAME}.tar.gz"
rm -rf ${APPLICATION_NAME}
ln -sf ${APPLICATION_FINAL_NAME} ${APPLICATION_NAME}

cd ${DIR}/${APPLICATION_NAME}/
if [ ! -d "$APPLICATION_LOG" ]; then
  mkdir -p "$APPLICATION_LOG"
fi
ln -sf ${APPLICATION_LOG} logs

cd ${DIR}/${APPLICATION_NAME}/bin
sh ./run.sh -restart

cd ${DIR}/${APPLICATION_NAME}
chmod 777 nohup.out
