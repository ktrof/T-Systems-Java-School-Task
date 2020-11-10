#!/bin/bash
mvn clean package
docker-compose up --build --remove-orphans