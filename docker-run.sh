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
    docker-compose stop final-consumer
    docker-compose rm -f final-consumer
    mvn clean install -pl final-consumer -DskipTests=true
    docker-compose build --no-cache final-consumer
}

build() {
    docker-compose stop producer
    docker-compose stop consumer
    docker-compose stop final-consumer
    docker-compose rm -f producer
    docker-compose rm -f consumer
    docker-compose rm -f final-consumer
    mvn clean install -DskipTests=true
    docker-compose build --no-cache
}

tail() {
    docker-compose logs -f
}

cluster_build() {
    docker-compose -f docker-cluster-compose.yml stop producer
    docker-compose -f docker-cluster-compose.yml stop consumer-1
    docker-compose -f docker-cluster-compose.yml stop consumer-2
    docker-compose -f docker-cluster-compose.yml stop final-consumer-1
    docker-compose -f docker-cluster-compose.yml stop final-consumer-2
    docker-compose -f docker-cluster-compose.yml rm -f producer
    docker-compose -f docker-cluster-compose.yml rm -f consumer-1
    docker-compose -f docker-cluster-compose.yml rm -f consumer-2
    docker-compose -f docker-cluster-compose.yml rm -f final-consumer-1
    docker-compose -f docker-cluster-compose.yml rm -f final-consumer-2

    mvn clean install -DskipTests=true
    docker-compose -f docker-cluster-compose.yml build --no-cache
}

cluster_start() {
    docker-compose -f docker-cluster-compose.yml up -d
}

cluster_down() {
    docker-compose -f docker-cluster-compose.yml down
}

cluster_tail() {
    docker-compose -f docker-cluster-compose.yml logs -f
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
  cluster_build_start)
    cluster_down
    cluster_build
    cluster_start
    cluster_tail
    ;;
  cluster_start)
    cluster_start
    cluster_tail
    ;;
  cluster_down)
    cluster_down
    ;;
  cluster_tail)
    cluster_tail
    ;;
  *)
    echo "Usage: $0 {build_start|build_producer_start|build_consumer_start|build_final_consumer_start|start|stop|tail|cluster_build_start|cluster_start|cluster_down|cluster_tail}"
esac
