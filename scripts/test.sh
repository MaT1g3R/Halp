#!/bin/bash
cd AndroidApp || exit
android-wait-for-emulator
adb shell input keyevent 82 &
./gradlew assembleDebug --stacktrace
./gradlew test --stacktrace
test_exit_code=$?
cd ..
exit "$test_exit_code"
