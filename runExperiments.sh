#!/bin/bash

./runner.sh 50 50 1 10000 1 results/results50_50_1_10000_1.csv
./runner.sh 50 50 1 10000 10 results/results50_50_1_10000_10.csv
./runner.sh 50 50 1 10000 20 results/results50_50_1_10000_20.csv
./runner.sh 50 50 1 10000 30 results/results50_50_1_10000_30.csv
./runner.sh 50 50 1 10000 40 results/results50_50_1_10000_40.csv
./runner.sh 50 50 1 10000 50 results/results50_50_1_10000_50.csv

./runner.sh 50 50 0 10000 1 results/results50_50_0_10000_1.csv
./runner.sh 50 50 0 10000 10 results/results50_50_0_10000_10.csv
./runner.sh 50 50 0 10000 20 results/results50_50_0_10000_20.csv
./runner.sh 50 50 0 10000 30 results/results50_50_0_10000_30.csv
./runner.sh 50 50 0 10000 40 results/results50_50_0_10000_40.csv
./runner.sh 50 50 0 10000 50 results/results50_50_0_10000_50.csv
