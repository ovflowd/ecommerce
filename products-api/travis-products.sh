#!/bin/bash -xe

./grailsw compile
./grailsw test-app
./gradlew cobertura
./gradlew check jacocoTestReport

mv build/test-results ../
ls -la ../

bash <(curl -s https://codecov.io/bash)