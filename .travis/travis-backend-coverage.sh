#!/usr/bin/env bash

mvn -f skillful_network_server/pom.xml -Pcoveralls -DTRAVIS_JOB_ID=$TRAVIS_JOB_ID clean test jacoco:report coveralls:report -Dspring-boot.run.profiles=test
