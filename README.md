# Cat Facts as a Service (CFaaS) #

An example RESTful service

## Run ##

'mvn spring-boot:run'

## Use service ##

Get a random cat fact! 3 to choose from! 

`curl localhost:8080/catFact`

## Deploy Docker Image ##

'mvn clean package docker:build'

Set environment variable DOCKER_HOST=tcp://hostname:2375 to run against a remote Docker host.
