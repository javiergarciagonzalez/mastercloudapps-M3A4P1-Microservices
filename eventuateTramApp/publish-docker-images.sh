#! /bin/bash -e

BRANCH=$(git rev-parse --abbrev-ref HEAD)

export DOCKER_IMAGE_TAG=latest

./gradlew javaDevelopmentComposePull || echo no image to pull

./gradlew javaDevelopmentImageComposeBuild

docker login -u ${DOCKER_USER_ID?} -p ${DOCKER_PASSWORD?}

./gradlew javaDevelopmentImageComposePush
