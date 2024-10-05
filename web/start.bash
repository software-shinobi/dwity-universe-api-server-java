#!/bin/bash

set -e

set -x

docker-compose build

docker-compose up -d
