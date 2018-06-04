#!/bin/bash

DATE=$(date +%Y%m%d)
DIR="/web/freeib"
APPLICATION_NAME="task"
APPLICATION_FINAL_NAME="freeib-task-test-"${DATE}
APPLICATION_LOG="/var/log/freeib/task/"

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
