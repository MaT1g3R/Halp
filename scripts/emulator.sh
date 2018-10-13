#!/bin/bash
android list targets | grep -E '^id:' | awk -F '"' '{$1=""; print $2}' # list all targets
echo no | android create avd --force -n test -t android-24 --abi armeabi-v7a
emulator -avd test -no-skin -no-window &
