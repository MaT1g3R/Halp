#!/bin/bash
cd AndroidApp || exit
./gradlew connectedAndroidTest
cd ..
