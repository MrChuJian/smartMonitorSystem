#!/bin/sh
cd `dirname $0`

if [ -z $log_level ]; then
  export log_level=WARN
fi
java -jar app.jar
