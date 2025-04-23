# Purchase Cart Service

This is test service for purchase cart

## Requirements

- Docker installed

## Build

For build docker image you must run:

- docker build -t purchase-cart-service .

## Build project

You must build project before run, with command:

- docker run -v $(pwd):/mnt -p 9090:9090 -w /mnt purchase-cart-service ./scripts/build.sh

## Run tests

If you want run tests, you must run:

- docker run -v $(pwd):/mnt -p 9090:9090 -w /mnt purchase-cart-service ./scripts/test.sh

## Run application

If you want run application, you must run:

- docker run -v $(pwd):/mnt -p 9090:9090 -w /mnt purchase-cart-service ./scripts/run.sh