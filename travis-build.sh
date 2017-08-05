#!/bin/bash -xe

cd products-api/
./travis-products.sh
cd ../purchase-api
./travis-purchase.sh
cd ../