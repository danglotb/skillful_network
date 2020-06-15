#!/usr/bin/env bash

mvn -Pcoveralls -DTRAVIS_JOB_ID=$TRAVIS_JOB_ID -DdoIntegrationTests=true clean test jacoco:report coveralls:report
