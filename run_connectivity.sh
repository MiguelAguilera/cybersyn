
rm effconnectivity/data/*
effconnectivity/data-forum
effconnectivity/data-stats
cd effconnectivity/data/
ls *.txt > files
cd ..
cd ..

javac effconnectivity/*.java

DATE=`date +%Y-%m-%d`

java effconnectivity.Connectivity 1 > results/connectivity/x-corr/$DATE.csv
java effconnectivity.Connectivity 2 8 2 > results/connectivity/mutual-information/$DATE.csv
java effconnectivity.Connectivity 3 8 2 2 > results/connectivity/transfer-entropy/$DATE.csv

rm effconnectivity/data/*
