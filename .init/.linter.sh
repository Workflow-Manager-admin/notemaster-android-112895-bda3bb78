#!/bin/bash
cd /home/kavia/workspace/code-generation/notemaster-android-112895-bda3bb78/notes_frontend
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

