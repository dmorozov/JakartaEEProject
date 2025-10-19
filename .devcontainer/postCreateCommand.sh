#!/bin/bash

# TODO: install global tools here

git config --global pull.rebase false

sudo chmod 666 /var/run/docker.sock

cd /workspaces
npm install --silent --save-dev --save-exact prettier
npm install --silent --save-dev prettier-plugin-jsp