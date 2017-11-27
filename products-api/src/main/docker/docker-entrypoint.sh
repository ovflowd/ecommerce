#!/bin/bash
set -e

exec java ${JAVA_OPTS} -Dserver.port=${SERVER_PORT} -jar application.jar $@