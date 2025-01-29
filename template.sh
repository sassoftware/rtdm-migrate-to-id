#!/usr/bin/env bash
# This script is _destructive_! It runs gomplate and then replaces
# all contents of the current directory with the template output.

gomplate --verbose
rc="$?"
if [ $rc -ne 0 ]; then
    exit $rc
fi

git stash push -m "template input data backup" -- template-data.yaml

# remove all contents of current directory except gomplate out and .git
shopt -s dotglob
for f in *
do
    case $f in
        .git) true;;
        out) true;;
        *) rm -rf "$f";;
    esac
done

# move all contents of gomplate out to current directory
mv -vf out/* .
rm -r out
