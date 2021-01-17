#!/bin/bash
#title          :build.sh
#description    :This script builds the payment service
#author         :Wassim
#==============================================================================

set -e

mvn clean package -Dmaven.test.skip=true

docker build -t payments .

docker-compose up -d --build

sleep 10s

mvn test

docker-compose down