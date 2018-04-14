#!/bin/sh
cd `dirname $0`
CDIR=`pwd`

git pull

version="$1"
if [ -z "$version" ]; then
	version=`date +%Y%m%d%H%M%S`
fi
 
mvn clean package -Dmaven.test.skip=true

cd ${CDIR}

docker build -t 120.77.34.35:5000/smartMonitorSystem:${version} .

docker push 120.77.34.35:5000/smartMonitorSystem:${version}
