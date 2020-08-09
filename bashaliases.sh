#!/usr/bin/env bash

export TOURING_HOME=$(pwd)

function start() {
    cd $TOURING_HOME
    docker-compose stop && docker-compose build && docker-compose up -d && docker-compose logs -f
}

function restart() {
  docker-compose stop
  docker-compose build
  docker-compose up
  docker-compose logs -f
}

function restartServer() {
  docker-compose stop server
  docker-compose build server
  docker-compose up -d server
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

# Tear down and remove all docker containers, images and volumes
function dockerDeepClean {
  dockerStopAll
  dockerRmAll
  dockerRmiAll
  dockerVolumeRmAll
}