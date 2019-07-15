#!/bin/bash
docker run --rm -it --name pek-passwd-gen -h pek-passwd-gen -v $HOME/project/peknight/security/applications.xml:/conf/applications.xml pek/password-generator:0.0.1-SNAPSHOT
