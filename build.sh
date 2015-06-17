#!/bin/bash

    # Licensed to the Apache Software Foundation (ASF) under one or more
    # contributor license agreements.  See the NOTICE file distributed with
    # this work for additional information regarding copyright ownership.
    # The ASF licenses this file to You under the Apache License, Version 2.0
    # (the "License"); you may not use this file except in compliance with
    # the License.  You may obtain a copy of the License at

    #     http://www.apache.org/licenses/LICENSE-2.0

    # Unless required by applicable law or agreed to in writing, software
    # distributed under the License is distributed on an "AS IS" BASIS,
    # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    # See the License for the specific language governing permissions and
    # limitations under the License.


PRJ="$(cd `dirname $0` && pwd)"

if [ -z "$WORKSPACE" ]; then
    export WORKSPACE="$PRJ"
    CREATE_PRIV_LOCAL_REPO=0
else
    CREATE_PRIV_LOCAL_REPO=1
fi

export HOME="$WORKSPACE"

if [ -z "$MAVEN_HOME" ]; then
    MAVEN_HOME=/auto/surf-tp/configs/dascode/tools/apache-maven-3.1.1
    MVN_GS=""
else
    x=${MVN_GS:= -gs $PRJ/.m2/settings.xml}
fi
export MAVEN_HOME

x=${JAVA_HOME:=/auto/java/jdks/lnx/jdk1.7.0_latest}
export JAVA_HOME
export PATH=$JAVA_HOME/bin:$PATH

x=${MVN_OPTIONS:=-e -B --fail-at-end --update-snapshots}
x=${MVN_GOAL:=clean install}

umask 0022

export MVN="$MAVEN_HOME/bin/mvn${MVN_GS}"
if [ $CREATE_PRIV_LOCAL_REPO -eq 1 ]; then
    MVN_REPO="$PRJ/.repository"
    mkdir -p "$MVN_REPO"
    MVN="$MVN -Dmaven.repo.local=$MVN_REPO"
fi

echo "(export MVN_OPTIONS=\"$MVN_OPTIONS\"; export MVN_GOAL=\"$MVN_GOAL\"; $PRJ/build.sh)"

set -x

cd $PRJ
source release-pre.incl.sh
env|sort

$MVN $MVN_OPTIONS $MVN_GOAL $*
