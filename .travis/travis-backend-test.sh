#!/usr/bin/env bash

mvn clean test -f skillful_network_server/pom.xml -Dspring-boot.run.profiles=test
