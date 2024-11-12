# Get started with Manticore in docker 

## Prerequisites

* Install Docker https://docs.docker.com/install/linux/docker-ce/ubuntu/
* Install Docker Compose https://docs.docker.com/compose/install/
&NewLine;
&NewLine;

## Manticore configuration

* Fill credentials ./docker/conf/manticore/secret.properties
* Check configs ./docker/conf/manticore/server.properties
&NewLine;
&NewLine;

## Build docker image

maven goal:

    mvn clean package -Pdocker
&NewLine;
&NewLine;

## Manticore in docker

start:

    docker-compose -f ./docker/docker-compose.yml up -d

stop:

    docker-compose -f ./docker/docker-compose.yml stop
    
delete containers (save db):

    docker-compose -f ./docker/docker-compose.yml rm
    
delete all with db:

    docker-compose -f ./docker/docker-compose.yml down -v

&NewLine;
&NewLine;

## Testing and debug

HTTP access: http://localhost:8080/

MySQL access:
* port: 13306
* database: manticore
* user: manticore
* pass: manticore

Remote debug:

    -agentlib:jdwp=transport=dt_socket,address=55555,suspend=n,server=y

&NewLine;
&NewLine;
