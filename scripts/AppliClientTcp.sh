#!/bin/bash

MAVEN_REPOS=${HOME}/.m2/repository
LOG4J_JAR=${MAVEN_REPOS}/log4j/log4j/1.2.17/log4j-1.2.17.jar

PROJECTROOT=~/Documents/CSC/CSC4509/DM/csc4509-dm/

export CLASSPATH=$LOG4J_JAR:$PROJECTROOT/target/classes

java tsp.csc4509.dm.appli.AppliClientTcp $*