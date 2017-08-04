#!/bin/bash -xe

./grailsw compile --non-interactive
./grailsw test-app :unit --non-interactive