#!/bin/bash
mvn -pl ticket-to-ride clean verify
cd ticket-to-ride || exit
docker-compose build --no-cache server
docker-compose up --remove-orphans