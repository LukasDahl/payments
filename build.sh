#!/bin/bash
#title          :build.sh
#description    :This script builds the payment service
#author         :Wassim
#==============================================================================

set -e

mvn clean package

docker build -t payments .