## Iris Predictor

A quick and dirty implementation of a Boot-based web service that uses H2O to make predictions using the [Iris data set](https://en.wikipedia.org/wiki/Iris_flower_data_set).

To support loading new prediction models without redeploying, the H2O models are compiled separately and stored in the database as jar files; then, they can be loaded at runtime.
 To illustrate this, a helper script trains both a Random Forest and a GBM using the iris data set and stores them in the database on application startup.

Have a look at the code to see how new (computed) features can be added easily.

## Build
Run `gradle build` to run the script that creates the classifiers and sets the jar files inside the `resources` folder.
The build file takes care of placing the generated models using custom `gradle` tasks.

## Usage
Run `gradle bootRun` to start the embedded Tomcat server. `POST` JSON requests to get predictions that can be queried using
spring data rest.

#### Example prediction request
```
    curl -X POST -H "Content-Type: application/json" -d '{
        "sepalLength" : 6.9,
        "sepalWidth" : 3.1,
        "petalLength" : 14.9,
        "petalWidth" : 1.5
    }' "http://localhost:8080/predict"
```
#### Example database query request
```
    curl -X GET -H "Content-Type: application/json" "http://localhost:8080/predictions"
```

### References

* [H2O consumer loan sample](https://github.com/h2oai/app-consumer-loan)
* [H2O consumer loan sample presentation](https://www.youtube.com/watch?v=jSN2y6j0Mxk)
* [Spring boot reference](http://docs.spring.io/spring-boot/docs/current/reference/html/)
