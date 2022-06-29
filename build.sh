#!/bin/bash
mvn clean package -Pnative -Dquarkus.container-image.build=true -Dquarkus.container-image.push=true
docker image prune -f
