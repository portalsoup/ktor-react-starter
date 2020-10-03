#!/usr/bin/env bash

export TOURING_HOME=$(pwd)

function start {
  cd $TOURING_HOME
  ./gradlew build
  docker-compose -f docker/dev/docker-compose.yml down
  docker-compose -f docker/dev/docker-compose.yml up -d
}

function stop {
  cd $TOURING_HOME
  ./gradlew build
  docker-compose -f docker/dev/docker-compose.yml down
}

function logs {
  cd $TOURING_HOME
  docker-compose -f docker/dev/docker-compose.yml logs -f
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