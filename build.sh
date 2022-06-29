#!/bin/bash
mvn clean package -Dquarkus.container-image.build=true -Dquarkus.container-image.push=true
docker image prune -f
