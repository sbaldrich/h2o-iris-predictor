#!/usr/bin/env bash
# Train a Random Forest and a GBM and place their jar files on the tmp directory.

mkdir tmp
echo "Training the random forest and generating its jar file"
r -f iris_random_forest.R
cd tmp
cat <(echo -e package com.baldrichcorp.ml.generated\;\\n) RFIrisPredictor.java > tmp.java
mv tmp.java RFIrisPredictor.java
javac -d . RFIrisPredictor.java -cp h2o-genmodel.jar
jar cf RFIrisPredictor.jar com
rm -r com RFIrisPredictor.java
cd ..

echo "Training the random forest and generating its jar file"
r -f iris_gbm.R
cd tmp
cat <(echo -e package com.baldrichcorp.ml.generated\;\\n) GBMIrisPredictor.java > tmp.java
mv tmp.java GBMIrisPredictor.java
javac -d . GBMIrisPredictor.java -cp h2o-genmodel.jar
jar cf GBMIrisPredictor.jar com
rm -r com GBMIrisPredictor.java