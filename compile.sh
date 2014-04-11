#!/bin/bash
mkdir -p bin
find . -name "*.java" > java_files.txt
javac -d bin -cp . @java_files.txt
