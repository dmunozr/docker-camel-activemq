#!/bin/sh

start() {
    docker-compose up -d
}

down() {
    docker-compose down
}

build_producer() {
    docker-compose stop producer
    docker-compose rm -f producer
    mvn clean install -pl producer -DskipTests=true
    docker-compose build --no-cache producer
}

build_consumer() {
    docker-compose stop consumer
    docker-compose rm -f consumer
    mvn clean install -pl consumer -DskipTests=true
    docker-compose build --no-cache consumer
}

build_final_consumer() {
    docker-compose stop final_consumer
    docker-compose rm -f final_consumer
    mvn clean install -pl final-consumer -DskipTests=true
    docker-compose build --no-cache final_consumer
}

build() {
    docker-compose stop producer
    docker-compose stop consumer
    docker-compose stop final_consumer
    docker-compose rm -f producer
    docker-compose rm -f consumer
    docker-compose rm -f final_consumer
    mvn clean install -DskipTests=true
    docker-compose build --no-cache
}

tail() {
    docker-compose logs -f
}

case "$1" in
  build_start)
    down
    build
    start
    tail
    ;;
  build_producer_start)
    build_producer
    start
    tail
    ;;
  build_consumer_start)
    build_consumer
    start
    tail
    ;;
  build_final_consumer_start)
    build_final_consumer
    start
    tail
    ;;
  start)
    start
    tail
    ;;
  stop)
    down
    ;;
  tail)
    tail
    ;;
  *)
    echo "Usage: $0 {build_start|build_producer_start|build_consumer_start|build_final_consumer_start|start|stop|tail}"
esac
