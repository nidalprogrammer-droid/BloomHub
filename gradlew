#!/bin/sh

#
# Copyright © 2015-2021 the original authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to set APP_HOME
# Resolve links: $0 may be a symlink
app_path=$0

# Need this for daisy-chained symlinks.
while
    APP_HOME=${app_path%"${app_path##*/}"}
    [ -h "$app_path" ]
do
    ls=$( ls -ld "$app_path" )
    link=${ls#*' -> '}
    case $link in #(
      /*) app_path=$link ;; #(
      *) app_path=$APP_HOME$link ;;
    esac
done

APP_HOME=$( cd "${APP_HOME%.}" && pwd -P ) || exit

appname="${0##*/}"
die() {
    echo "$*"
    exit 1
}

# Allow when executed from different directory
if [ -z "$GRADLE_USER_HOME" ]; then
  GRADLE_USER_HOME="$HOME/.gradle"
fi

readonly GRADLE_USER_HOME

exec java \
  -classpath "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" \
  -Dorg.gradle.wrapper.properties="$APP_HOME/gradle/wrapper/gradle-wrapper.properties" \
  org.gradle.wrapper.GradleWrapperMain \
  "$@"
