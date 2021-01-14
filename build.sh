#!/bin/bash
#title          :build.sh
#description    :This script builds the payment service
#author         :Wassim
#==============================================================================

set -e

./mvnw package -Dquarkus.package.type=uber-jar

docker build -t payments .