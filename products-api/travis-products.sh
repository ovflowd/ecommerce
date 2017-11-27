#!/bin/bash -xe

./grailsw compile
./grailsw test-app
./gradlew cobertura
./gradlew check jacocoTestReport