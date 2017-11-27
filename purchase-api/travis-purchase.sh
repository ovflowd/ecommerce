#!/bin/bash -xe

./grailsw compile
./grailsw test-app
./gradlew assemble buildDockerImage