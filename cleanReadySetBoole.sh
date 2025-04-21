#!/bin/sh
find ./fr/rvander/ready_set_boole -name "*.class" -exec rm {} +
if [ -f "sources.txt" ]; then
    rm sources.txt
fi