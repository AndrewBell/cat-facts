# Cat Facts as a Service (CFaaS) #

An example RESTful service

## Run ##

`mvn spring-boot:run`

## Unit Test ##

To run unit tests only

`mvn test`

## Integration Test ##

To run both unit and integration tests

`mvn integration-test`

## Use service ##

Get a random cat fact! 3 to choose from! 

`curl localhost:8080/catfact/random`

## Create Docker Image ##

You can call the `docker:build` step directly

`mvn clean -U package docker:build`

Or, if you'd like to ensure integration tests are run

`mvn clean -U install`

Set environment variable DOCKER_HOST=tcp://hostname:2375 to run against a remote Docker host.

## Docker Run ##

Here is an example Docker run command

`docker run -d -p 9000:8080 -e recursivechaos/catfacts`