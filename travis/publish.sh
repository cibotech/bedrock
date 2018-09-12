#!/usr/bin/env bash
# Publishes docs container

set -ev  # exit after first error and print all commands
pip install --user awscli                    # install aws cli w/o sudo
export PATH=$PATH:$HOME/.local/bin           # put aws in the path
export BEDROCK_ENV_PROD=true              # gulp knows that its prod
$(aws ecr get-login --region us-east-1 --no-include-email) # needs AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY envvars

if [[ $TRAVIS_BRANCH == "master" && $TRAVIS_PULL_REQUEST == "false" ]]; then
    #Build the frontend dockerfile
    sbt fullOptJS
    gulp build
    ls ./.temp/bedrock/
    docker build -t bedrock .
    docker tag bedrock $BEDROCK_DOCKER_REPO:$TRAVIS_BUILD_NUMBER
    docker push $BEDROCK_DOCKER_REPO:$TRAVIS_BUILD_NUMBER
else
    echo "Branch '$TRAVIS_BRANCH' != 'master' or build is a PR build - skipping publish step"
fi