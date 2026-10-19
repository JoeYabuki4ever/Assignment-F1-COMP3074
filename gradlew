#!/bin/sh
APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
if [ ! -f "$WRAPPER_JAR" ]; then
    echo 'Downloading the official Gradle 8.9 wrapper JAR...'
    URL='https://raw.githubusercontent.com/gradle/gradle/v8.9.0/gradle/wrapper/gradle-wrapper.jar'
    if command -v curl >/dev/null 2>&1; then
        curl --fail --location --silent --show-error "$URL" -o "$WRAPPER_JAR" || exit 1
    elif command -v wget >/dev/null 2>&1; then
        wget -q "$URL" -O "$WRAPPER_JAR" || exit 1
    else
        echo 'Please install curl or wget to bootstrap the Gradle wrapper.' >&2
        exit 1
    fi
fi
if [ -n "$JAVA_HOME" ]; then JAVA="$JAVA_HOME/bin/java"; else JAVA=java; fi
exec "$JAVA" -Xmx64m -Xms64m -classpath "$WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
