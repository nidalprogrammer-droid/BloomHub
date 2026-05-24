#!/bin/bash
# This script downloads and sets up the Gradle wrapper JAR
# Run this locally: bash gradle/wrapper/setup-wrapper.sh

set -e

GRADLE_VERSION="8.4"
JAR_URL="https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip"
WRAPPER_DIR="gradle/wrapper"

echo "Downloading Gradle ${GRADLE_VERSION}..."
mkdir -p "$WRAPPER_DIR"
cd "$WRAPPER_DIR"

# Download the Gradle distribution
curl -L "$JAR_URL" -o gradle.zip

# Extract the wrapper JAR
unzip -j gradle.zip "gradle-${GRADLE_VERSION}/lib/gradle-wrapper.jar" -d .

# Extract gradle-wrapper.jar from the distribution
rm gradle.zip

echo "Gradle wrapper JAR downloaded successfully to $WRAPPER_DIR/gradle-wrapper.jar"
