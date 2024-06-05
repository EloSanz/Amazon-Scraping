#!/bin/sh

SERVER="localhost"
PORT="8080"
ENDPOINT="/api/products/fetch"
PRODUCT_URL="https%3A%2F%2Fwww.amazon.com%2Fgp%2Fproduct%2FB00SMBFZNG"
SLEEP_TIME=1

curl -X POST "$SERVER:$PORT$ENDPOINT?url=$PRODUCT_URL"
sleep $SLEEP_TIME

