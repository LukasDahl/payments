#!/bin/bash
#title          :build.sh
#description    :This script builds the payment service
#author         :Wassim
#==============================================================================

set -e

mvn clean package -Dmaven.test.skip=true

docker build -t payments .

docker-compose up -d rabbitMq

sleep 40s

docker-compose up -d payments

mvn test

docker-compose down