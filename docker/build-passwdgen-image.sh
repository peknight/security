#!/bin/bash
cd ../
mvn clean package
cp -r target/lib docker/
cd docker/
docker build -t pek/password-generator:0.0.1-SNAPSHOT .
rm -rf lib/
