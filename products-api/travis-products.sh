#!/bin/bash -xe

./grailsw compile
./grailsw test-app

mv test-results ../

bash <(curl -s https://codecov.io/bash)