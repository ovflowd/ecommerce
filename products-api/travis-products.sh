#!/bin/bash -xe

./grailsw compile
./grailsw test-app

bash <(curl -s https://codecov.io/bash)