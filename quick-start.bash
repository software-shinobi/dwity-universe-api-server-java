#!/bin/bash

##

set -e

set -x

##

reset

clear

##

echo "*** "
echo "*** Docker Container START: textformatdashboard.online/text-format-dashboard-api-java"
echo "*** "

# docker run -p 44444:8080 textformatdashboard.online/text-format-dashboard-api-java

docker-compose down

docker-compose up -d

## 

echo "fin"