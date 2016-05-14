# Cat Facts as a Service (CFaaS) #

A RESTful service that provides users with random cat facts, as well as the ability to submit their own.

## Test ##

### Unit Tests ###

To run unit tests only

`mvn test`

### Integration Tests ###

Integration tests can be called directly via the failsafe plugin

`mvn test-compile failsafe:integration-test`

### All Tests ###

To run both unit and integration tests

`mvn integration-test`

## Usage ##

### Run ###

This project uses Spring profiles to load properties. If you do not provide a profile, the 'default' profile is used.
Spring Security will then generate a random password in the logs during startup you can use to access the admin functionality.
If you'd like to create your own profile, create a properties file called application-yourprofile.yml in src/main/resources. 
You can see application-example.yml for an example.

generated password: `mvn spring-boot:run`

provided password: `mvn spring-boot:run -Dspring.profiles.active=yourprofile`

### Use service ###

Get a random cat fact!

`curl localhost:8080/catfacts/random`

## Deployment ##

### Create Docker Image ###

The Spotify docker-maven-plugin allows us to build a Docker image as part of the Maven 'install' phase. 
By default, this is skipped, so override the skipDockerBuild property.

`mvn install -DskipDockerBuild=false`

You may consider adding a application-docker.yml properties file, as the Docker entrypoint will specify a 'docker' profile.
Set an environment variable DOCKER_HOST=tcp://hostname:2375 to run against a remote Docker host.

### Docker Run ###

Here is an example Docker run command

`docker run -d -p 9000:8080 -e recursivechaos/catfacts`
