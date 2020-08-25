https://gitlab.com/nanodeath/ktor-session-auth-example

# Prerequisites

* Java 8
* NodeJS
* NPM
* Docker
* docker-compose

# Run the app
In the root of the repository, run

    $ ./gradlew build

This will produce a server artifact, located in the [web](web/build/libs/shadow.jar) module, and 
a client bundle.

Then, run

    $ docker-compose up
    
To start the app.  Only the web and client modules talk on the network.

    web: localhost:8080
    client: localhost:80


# Gradle Modules
The project consists of 5 modules that each contain a different app layer.

## data
The data module contains all the definitions for the postgres database.  This 
includes table definitions and entities.

## common
The common module contains data classes and other common definitions to be shared
between all other kotlin modules.  Business logic generally should not go here.

## core
The core module contains core business logic and acts as the intermediary layer
between database and the web server

## web
The web module is the serverside entrypoint to the app.  It starts a webserver
and holds all the api routes.

## client
The only module that isn't kotlin, this module contains the ReactJS frontend which
is to be intended to be served using nginx

This image illustrates the dependencies between modules
![](docs/modules.jpeg)


# Data migrations

When performing a data migration, a few steps need to be taken.

## Exposed
First, in the `data` module, create/modify/delete any table definition, then update
entity definitions on that table.

## Flyway
Second, in the `web` module's resources folder.  Create a new file in db.migration
that follows the format `V#__Simple_Description.sql` where V# is the next incremented
number in the sequence consistent with all the other migration files.  This file should
be a valid postgres script.

For example: `V1__Add_Traveler_Table.sql` 