#!/usr/bin/env bash

export TOURING_HOME=$(pwd)

function build {
  cd $TOURING_HOME
  ./gradlew shadowJar
  docker-compose build
}

function cleanBuild {
  cd $TOURING_HOME
  ./gradlew shadowJar
  docker-compose down --no-cache
}

function deploy {
  cd $TOURING_HOME

  ./gradlew clean
  ./gradlew copy
  ./gradlew artifact
  docker-compose down
  docker-compose up server
}

function stop {
  cd $TOURING_HOME
  docker-compose down
}

function logs {
  cd $TOURING_HOME
  docker-compose logs -f
}

# Attempt to stop all running docker containers
function dockerStopAll {
  docker stop $(docker ps -aq)
}

# Attempt to remove all docker containers
function dockerRmAll {
  docker rm -f $(docker ps -aq)
}

# Attempt to remove all docker images
function dockerRmiAll {
  docker rmi -f $(docker images -aq)
}

# Attempt to remove all docker volumes
function dockerVolumeRmAll {
  docker volume rm $(docker volume ls -q)
}

function dockerNetworkRmAll {
  docker network rm $(docker network rm -q)
}

# Obliterate all docker state
function nukeDocker {
  dockerStopAll
  dockerRmAll
  dockerRmiAll
  dockerVolumeRmAll
  dockerNetworkRmAll
}