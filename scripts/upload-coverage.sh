#!/usr/bin/env bash

set -o pipefail

echo "Uploading Coverage Report"
bash <(curl -s https://codecov.io/bash)
