#!/bin/bash -xe

./grailsw compile
./grailsw test-app
./gradlew cobertura
./gradlew check jacocoTestReport

bash <(curl -s https://codecov.io/bash)